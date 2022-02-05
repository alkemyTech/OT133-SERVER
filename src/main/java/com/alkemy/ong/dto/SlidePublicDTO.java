package com.alkemy.ong.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Comparator;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SlidePublicDTO {

	@NotBlank(message = "no es un valor valido.")
	private String text;
	
	@Min(message = "debe ser igual o mayor a 1", value = 1)
	private Integer orderNumber;
	
	private LocalDateTime timestamps;
	
	private String imageUrl;

  public static Comparator<SlidePublicDTO> compareByOrder = new Comparator<SlidePublicDTO>() {
    @Override
    public int compare(SlidePublicDTO p2, SlidePublicDTO p1) {
      return p2.getOrderNumber().compareTo(p1.getOrderNumber());
    }
  };
}
