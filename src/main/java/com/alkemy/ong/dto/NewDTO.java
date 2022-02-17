package com.alkemy.ong.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Schema(description = "NewDTO")
public class NewDTO {

  @Schema(description = "New Name", required = true, example = "Juan")
  @ApiModelProperty(required=true)
  private String name;
  
  @Schema(description = "New Content", required = true, example = "This is content")
  @ApiModelProperty(required=true)
  private String content;
  
  @Schema(description = "New image", required = true, example = "/image.com")
  @ApiModelProperty(required=true)
  private String image;
  
  @Schema(description = "New ID Category", required = true, example = "4d64fa66-d48c-4d1e-892d-84069b78e917")
  @ApiModelProperty(required=true)
  private String categoryId;
  
  @ApiModelProperty(hidden = true)
  private LocalDateTime timestamps;
  @ApiModelProperty(hidden = true)
  private String id;  
}
