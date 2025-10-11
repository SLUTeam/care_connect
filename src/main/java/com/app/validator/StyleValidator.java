package com.app.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * 
 * @author Ulaganathan 
 */
@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class StyleValidator {
	
	/*
    @Autowired
    MessagePropertyService messageSource; 
    
    private @NonNull StyleRepository styleRepository;

    
	 public ValidationResult validate(StyleDTO styleDTO) {
		 
		List<String> errors = new ArrayList<>();
		Style styleObj = null;

		if (!ValidationUtil.isNull(styleDTO.getId())) {
            throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
        }
		
        Optional<Style> styleObjDuplicate = styleRepository.findByName(styleDTO.getName());
		
        if(styleObjDuplicate.isPresent()) {
			
			 String[] params = new String[]{styleDTO.getName()};
			 
			 errors.add( messageSource.getMessage("style.duplicate",params));
		}
        
        if (ValidationUtil.isNullOrEmpty(styleDTO.getName())) {
            errors.add(messageSource.getMessage("style.name.required"));
        } else {
        	styleDTO.setName(ValidationUtil.getFormattedString(styleDTO.getName()));
            if (!ValidationUtil.isValidName(styleDTO.getName())) {
                errors.add(messageSource.getMessage("style.name.invalid"));
            }
        }
        
        
         
        ValidationResult result = new ValidationResult();
        if (errors.size() > 0) {
            String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
            throw new ObjectInvalidException(errorMessage);
        }
        
        
        styleObj = Style.builder().name(styleDTO.getName()).description(styleDTO.getDescription()).sam(styleDTO.getSam()).build();
        
        
        result.setObject(styleObj);
        return result;
	 }
	 */
	
}