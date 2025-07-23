package com.youssef.real_estate_api.service.impl;

import com.youssef.real_estate_api.domain.*;
import com.youssef.real_estate_api.dto.PhotoDTO;
import com.youssef.real_estate_api.dto.PropertyRequestDTO;
import com.youssef.real_estate_api.dto.PropertyResponseDTO;
import com.youssef.real_estate_api.enums.PriceUnit;
import com.youssef.real_estate_api.enums.PropertyType;
import com.youssef.real_estate_api.exception.ResourceNotFoundException;
import com.youssef.real_estate_api.mapper.PropertyMapper;
import com.youssef.real_estate_api.repository.PhotoRepository;
import com.youssef.real_estate_api.repository.PropertyRepository;
import com.youssef.real_estate_api.repository.UserRepository;
import com.youssef.real_estate_api.service.PropertyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private EntityManager entityManager;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyMapper propertyMapper;
    private final PhotoRepository photoRepository;

                             //الخصائص:
//تعتمد على المستخدم الحالي (current logged-in user)، وليس على dto.getOwnerId().
//
//getCurrentUser() غالبًا تُستخدم مع Spring Security و Authentication أو SecurityContextHolder لاستخراج المستخدم من التوكن أو الجلسة.
//
//تفترض أن المالك هو الشخص الذي قام بطلب الإضافة، وهو سيناريو شائع في تطبيقات المستخدم النهائي.
//
//مناسبة لـ:
//أنظمة حيث المالك هو من يقوم بإضافة العقار، ولا يُسمح له بإدخال ownerId يدويًا.
//public PropertyResponseDTO create(PropertyRequestDTO dto) {
//    User currentUser = getCurrentUser();
//    Property property = addProperty(dto, currentUser);
//    return propertyMapper.toDTO(property);
//}

                              //الخصائص:
    //تعتمد على وجود ownerId داخل PropertyRequestDTO.
    //
    //يُسمح بإرسال المالك من خارج النظام، وقد يكون ذلك في حالة أن مسؤول (admin) هو من يُضيف العقارات بالنيابة عن المالكين.
    //
    //مناسبة لـ:
    //لوحة تحكم المدير حيث يمكنه إنشاء عقارات لمستخدمين آخرين.
    //
    //تطبيقات B2B (مثلاً: وكالة تأجير تضيف عقارات للملاك).
    @Override
    public PropertyResponseDTO create(PropertyRequestDTO dto) {
        // 1. استخدم المابّر لتحويل الـ DTO إلى Entity (مع كل الحقول الأساسية)
        Property property = propertyMapper.toEntity(dto);

        // 2. اجلب المستخدم من الـ userRepository
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner with id " + dto.getOwnerId() + " not found"));
        property.setOwner(owner);

        if (dto.getAddress() != null) {
            Address address = new Address();
            address.setCity(dto.getAddress().getCity());
            address.setStreet(dto.getAddress().getStreet());
            address.setZipCode(dto.getAddress().getZipCode());
            address.setProperty(property); // علاقة عكسية
            property.setAddress(address);
        }

        // 4. إعداد Promotion
        if (dto.getPromotion() != null) {
            Promotion promotion = new Promotion();
            promotion.setDiscountPercentage(dto.getPromotion().getDiscountPercentage());
            promotion.setStartDate(dto.getPromotion().getStartDate());
            promotion.setEndDate(dto.getPromotion().getEndDate());
            promotion.setProperty(property); // علاقة عكسية
            property.setPromotion(promotion);
        }

//---------med work---
        // 3. أضف العلاقة العكسية للصور (photo.setProperty)
//        if (property.getPhotos() != null) {
//            List<Photo> photos = new ArrayList<>();
//            List<PhotoDTO> photosTO = dto.getPhotos();
//            for (PhotoDTO photoDTO : photosTO) {
//                Photo foto = new Photo();
//                foto.setUrl(photoDTO.getUrl());
//                photos.add(foto);
//            }
//            property.setPhotos(photos);
//            photoRepository.saveAll(photos);
//        }
//
//        // 4. خزّن الـ Property (بما فيه كل شيء: العنوان، الصور، المستخدم، الترويج)
//        Property saved = propertyRepository.save(property);
        if (dto.getPhotos() != null) {
            List<Photo> photos = new ArrayList<>();
            for (PhotoDTO photoDTO : dto.getPhotos()) {
                Photo photo = new Photo();
                photo.setUrl(photoDTO.getUrl());
                photo.setProperty(property); // ✅ ضروري لربط الصور بالعقار
                photos.add(photo);
            }
            property.setPhotos(photos);
        }

        Property saved = propertyRepository.save(property);
        // 5. رجع DTO للرد
        return propertyMapper.toDTO(saved);
    }

    @Override
    public List<PropertyResponseDTO> getAll() {
        return propertyRepository.findAll()
                .stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<PropertyResponseDTO> filter(String city, Boolean promo, String type, String unit, Integer minRooms, Integer stars) {
        List<Property> properties = customFilter(city, promo, type, unit, minRooms, stars);

        if (properties.isEmpty()) {
            log.info("No properties found for filter criteria.");
            return List.of();
        }

        return properties.stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }



    //🟢 مميزات:
    //
    //الفلترة تتم مباشرة في قاعدة البيانات (SQL أو JPQL)، مما يعني:
    //
    //أداء أعلى، خصوصًا إذا كانت البيانات كبيرة.
    //
    //تحميل فقط العقارات التي تطابق المعايير.
    //
    //تنفذ SQL حسب الحاجة وليس تحميل كل البيانات ثم فلترتها في الذاكرة
//    public List<PropertyResponseDTO> filter(String city, Boolean promo, String type, String unit, Integer minRooms, Integer stars) {
//        List<Property> filtered = propertyRepository.customFilter(city, promo, type, unit, minRooms, stars);
//        return filtered.stream()
//                .map(propertyMapper::toDTO)
//                .collect(Collectors.toList());
//    }
                           //🔴 عيوب:
//
//يقوم أولًا بجلب كل العقارات من قاعدة البيانات findAll()، ثم يبدأ الفلترة في الذاكرة.
//
//إذا كانت قاعدة البيانات تحتوي آلاف العقارات → هذا يسبب بطء واستهلاك كبير للذاكرة.
//    @Override
//    public List<PropertyResponseDTO> filter(String city, Boolean promo, String type, String unit, Integer minRooms, Integer stars) {
//        return propertyRepository.findAll()
//                .stream()
//                .filter(p -> city == null || p.getCity().equalsIgnoreCase(city))
//                .filter(p -> promo == null || p.isPromo() == promo)
//                .filter(p -> type == null || p.getType().name().equalsIgnoreCase(type))
//                .filter(p -> unit == null || p.getPriceUnit().name().equalsIgnoreCase(unit))
//                .filter(p -> minRooms == null || p.getRooms() >= minRooms)
//                .filter(p -> stars == null || (p.getStars() != null && p.getStars() >= stars))
//                .map(propertyMapper::toDTO)
//                .collect(Collectors.toList());
//    }

                              //🔹 المزايا:
    //تعتمد على Spring Security.
    //
    //حقيقية وآمنة.
    //
    //تُرجع المستخدم المسجل دخوله فعليًا.
    //
    //تعتمد على الـ JWT أو Session الموجود.
    //
                           //🔸 العيوب:
    //تحتاج إعداد كامل لـ Spring Security وJWT.
    //
    //تحتاج أن يكون Authentication صالحًا.
    //  @Override
    //    public User getCurrentUser() {
    //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //        String email = authentication.getName();
    //        return userService.findByEmail(email);
    //    }

                           //🔹 المزايا:
    //بسيطة جدًا.
    //
    //تصلح للتجربة والاختبار في البداية.
    //
                           //🔸 العيوب:
    //غير واقعية.
    //
    //لا تستخدم نظام المصادقة (Authentication).
    //
    //تصلح فقط في مشروع بدون تسجيل دخول حقيقي.
    //
    //كل من يدخل يعتبره نفس المستخدم (id = 1)، حتى لو كان أكثر من مستخدم.
    @Override
    public User getCurrentUser() {
        // Simulate getting the current logged-in user (you can adapt this for Spring Security)
        // For now, return a user from DB (example with id = 1)
        return userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

//    public Property addProperty(PropertyRequestDTO dto, User user) {
//        Property property = propertyMapper.toEntity(dto);
//        property.setOwner(user);
//
//        // address
//        Address address = propertyMapper.dtoToAddress(dto.getAddress());
//        address.setProperty(property);
//        property.setAddress(address);
//
//        // photos
//        if (dto.getPhotos() != null) {
//            List<Photo> photos = dto.getPhotos().stream()
//                    .map(propertyMapper::dtoToPhoto)
//                    .peek(p -> p.setProperty(property))
//                    .collect(Collectors.toList());
//            property.setPhotos(photos);
//        }
//
//        // promotion
//        if (dto.getPromotion() != null) {
//            Promotion promo = propertyMapper.dtoToPromotion(dto.getPromotion());
//            promo.setProperty(property);
//            property.setPromotion(promo);
//        }
//
//        return propertyRepository.save(property);
//    }



    @Override
    public List<Property> customFilter(String city, Boolean promo, String type, String unit, Integer minRooms, Integer stars) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> query = cb.createQuery(Property.class);
        Root<Property> root = query.from(Property.class);

        List<Predicate> predicates = new ArrayList<>();

        if (city != null && !city.isEmpty()) {
            predicates.add(cb.equal(root.get("city"), city));
        }

        if (promo != null) {
            predicates.add(cb.equal(root.get("promo"), promo));
        }

        if (type != null && !type.isEmpty()) {
            try {
                predicates.add(cb.equal(root.get("type"), PropertyType.valueOf(type.toUpperCase())));
            } catch (IllegalArgumentException e) {
                // يمكن تسجيل خطأ أو تجاهل الشرط
            }
        }

        if (unit != null && !unit.isEmpty()) {
            try {
                predicates.add(cb.equal(root.get("priceUnit"), PriceUnit.valueOf(unit.toUpperCase())));
            } catch (IllegalArgumentException e) {
                // يمكن تسجيل خطأ أو تجاهل الشرط
            }
        }

        if (minRooms != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("rooms"), minRooms));
        }

        if (stars != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("stars"), stars));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }


}