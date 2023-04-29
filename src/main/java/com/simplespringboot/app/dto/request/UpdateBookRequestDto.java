package com.simplespringboot.app.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simplespringboot.app.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class UpdateBookRequestDto extends CreateBookRequestDto {}