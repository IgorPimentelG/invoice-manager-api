package com.ms.tax.calculator.infra.controllers.docs;

import com.ms.tax.calculator.domain.entities.TaxResume;
import com.ms.tax.calculator.infra.errors.NoTaxesPayableException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Calculate taxes", description = "Returns the result of the calculation", tags = {"Calculator"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "There are taxes to be paid", content = @Content(
	schema = @Schema(implementation = TaxResume.class)
  )),
  @ApiResponse(responseCode = "409", description = "There are no taxes to be paid", content = @Content(
	schema = @Schema(implementation = NoTaxesPayableException.class)
  ))
})
public @interface ApiOperationCalculate {}
