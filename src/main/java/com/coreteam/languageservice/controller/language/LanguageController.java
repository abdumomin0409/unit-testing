package com.coreteam.languageservice.controller.language;

import com.coreteam.languageservice.controller.BaseController;
import com.coreteam.languageservice.dto.language.LanguageCreateDTO;
import com.coreteam.languageservice.dto.language.LanguageUpdateDTO;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.language.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.coreteam.languageservice.controller.BaseURL.*;

@RestController
@RequestMapping(LANGUAGE_URL)
@Tag(name = "Language", description = "This API is used for Language")
public class LanguageController extends BaseController<LanguageService> {

    public LanguageController(LanguageService service) {
        super(service);
    }

    @Operation(summary = "This API is used for create Language", responses = {
            @ApiResponse(responseCode = "201", description = "Language Created", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @PostMapping(BASIC_CREATE_URL)
    public ResponseEntity<ResponseData<?>> create(@Valid @RequestBody LanguageCreateDTO dto) {
        ResponseData<?> body = service.create(dto);
        if (body.getSuccess())
            return ResponseEntity.status(201).body(body);
        return ResponseEntity.status(400).body(body);
    }

    @Operation(summary = "This API is used for update Language", responses = {
            @ApiResponse(responseCode = "202", description = "Language Update", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @PutMapping(BASIC_UPDATE_URL)
    public ResponseEntity<ResponseData<?>> update(@RequestBody @Valid LanguageUpdateDTO dto) {
        ResponseData<?> update = service.update(dto.getLanguage_id(), dto);
        if (update.getSuccess())
            return ResponseEntity.status(202).body(update);
        return ResponseEntity.status(400).body(update);
    }

    @Operation(summary = "This API is used for delete Language", responses = {
            @ApiResponse(responseCode = "202", description = "Language Deleted", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @DeleteMapping(BASIC_DELETE_BY_ID_URL)
    public ResponseEntity<ResponseData<?>> delete(@PathVariable Integer deletedID) {
        ResponseData<?> delete = service.delete(deletedID);
        if (delete.getSuccess())
            return ResponseEntity.status(202).body(delete);
        return ResponseEntity.status(400).body(delete);
    }

    @Operation(summary = "This API is used for get Language", responses = {
            @ApiResponse(responseCode = "200", description = "Language Get by ID", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_BY_ID_URL)
    public ResponseEntity<ResponseData<?>> getByID(@PathVariable Integer getID) {
        ResponseData<?> body = service.get(getID);
        if (body.getSuccess())
            return ResponseEntity.ok(body);
        return ResponseEntity.badRequest().body(body);
    }

    @Operation(summary = "This API is used for get all Language", responses = {
            @ApiResponse(responseCode = "200", description = "Language GET All", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_URL)
    public ResponseEntity<ResponseData<?>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "This API is used for get all active Language", responses = {
            @ApiResponse(responseCode = "200", description = "Language GET All Active", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_ACTIVE_URL)
    public ResponseEntity<ResponseData<?>> getAllActive() {
        return ResponseEntity.ok(service.getAllActive());
    }

    @Operation(summary = "This API is used for get all by status Language", responses = {
            @ApiResponse(responseCode = "200", description = "Language get all by status", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_BY_STATUS_ID_URL)
    public ResponseEntity<ResponseData<?>> getAllByStatusID(@PathVariable Integer statusID) {
        ResponseData<?> allByStatusID = service.getAllByStatusID(statusID);
        if (allByStatusID.getSuccess())
            return ResponseEntity.ok(allByStatusID);
        return ResponseEntity.badRequest().body(allByStatusID);
    }

}
