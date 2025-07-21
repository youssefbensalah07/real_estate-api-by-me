package com.youssef.real_estate_api.service.impl;

import com.youssef.real_estate_api.domain.*;
import com.youssef.real_estate_api.dto.PropertyRequestDTO;
import com.youssef.real_estate_api.dto.PropertyResponseDTO;
import com.youssef.real_estate_api.exception.ResourceNotFoundException;
import com.youssef.real_estate_api.mapper.PropertyMapper;
import com.youssef.real_estate_api.repository.PropertyRepository;
import com.youssef.real_estate_api.repository.UserRepository;
import com.youssef.real_estate_api.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyMapper propertyMapper;

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
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Property property = propertyMapper.toEntity(dto);
        property.setOwner(owner);
        Property saved = propertyRepository.save(property);

        log.info("Created property with ID: {}", saved.getId());
        return propertyMapper.toDTO(saved);
    }

    @Override
    public List<PropertyResponseDTO> getAll() {
        return propertyRepository.findAll()
                .stream()
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
    @Override
    public List<PropertyResponseDTO> filter(String city, Boolean promo, String type, String unit, Integer minRooms, Integer stars) {
        return propertyRepository.findAll()
                .stream()
                .filter(p -> city == null || p.getCity().equalsIgnoreCase(city))
                .filter(p -> promo == null || p.isPromo() == promo)
                .filter(p -> type == null || p.getType().name().equalsIgnoreCase(type))
                .filter(p -> unit == null || p.getPriceUnit().name().equalsIgnoreCase(unit))
                .filter(p -> minRooms == null || p.getRooms() >= minRooms)
                .filter(p -> stars == null || (p.getStars() != null && p.getStars() >= stars))
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }

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

    public Property addProperty(PropertyRequestDTO dto, User user) {
        Property property = propertyMapper.toEntity(dto);
        property.setOwner(user);

        // address
        Address address = propertyMapper.dtoToAddress(dto.getAddress());
        address.setProperty(property);
        property.setAddress(address);

        // photos
        if (dto.getPhotos() != null) {
            List<Photo> photos = dto.getPhotos().stream()
                    .map(propertyMapper::dtoToPhoto)
                    .peek(p -> p.setProperty(property))
                    .collect(Collectors.toList());
            property.setPhotos(photos);
        }

        // promotion
        if (dto.getPromotion() != null) {
            Promotion promo = propertyMapper.dtoToPromotion(dto.getPromotion());
            promo.setProperty(property);
            property.setPromotion(promo);
        }

        return propertyRepository.save(property);
    }
}