package com.coreteam.languageservice.controller.status;

import com.coreteam.languageservice.controller.BaseController;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.status.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.coreteam.languageservice.controller.BaseURL.*;

/**
 * Status Controller
 * Description: Controller for status
 */

@RestController
@RequestMapping(STATUS_URL)
@Tag(name = "Status", description = "This API is used for Status")
public class StatusController extends BaseController<StatusService> {

    public StatusController(StatusService service) {
        super(service);
    }

    @Operation(summary = "This API is used for get Status by id", responses = {
            @ApiResponse(responseCode = "200", description = "Status GET by ID", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_BY_ID_URL)
    public ResponseEntity<ResponseData<?>> getByID(@PathVariable Integer getID) {
        return ResponseEntity.ok(service.get(getID));
    }

    @Operation(summary = "This API is used for get all Status", responses = {
            @ApiResponse(responseCode = "200", description = "Status GET All", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_URL)
    public ResponseEntity<ResponseData<?>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

}
