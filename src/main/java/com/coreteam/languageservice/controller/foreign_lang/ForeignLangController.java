package com.coreteam.languageservice.controller.foreign_lang;

import com.coreteam.languageservice.controller.BaseController;
import com.coreteam.languageservice.dto.foreign_lang.ForeignLangCreateDTO;
import com.coreteam.languageservice.dto.foreign_lang.ForeignLangUpdateDTO;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.foreign_lang.ForeignLangService;
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
@RequestMapping(FOREIGN_LANG_URL)
@Tag(name = "Foreign Language", description = "This API is used for Foreign Language")
public class ForeignLangController extends BaseController<ForeignLangService> {

    public ForeignLangController(ForeignLangService service) {
        super(service);
    }

    @Operation(summary = "This API is used for create Foreign Language", responses = {
            @ApiResponse(responseCode = "201", description = "Foreign Language Created", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @PostMapping(BASIC_CREATE_URL)
    public ResponseEntity<ResponseData<?>> create(@Valid @RequestBody ForeignLangCreateDTO dto) {
        ResponseData<?> body = service.create(dto);
        if (body.getSuccess())
            return ResponseEntity.status(201).body(body);
        return ResponseEntity.status(400).body(body);
    }

    @Operation(summary = "This API is used for update Foreign Language", responses = {
            @ApiResponse(responseCode = "202", description = "Foreign Language Updated", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @PutMapping(BASIC_UPDATE_URL)
    public ResponseEntity<ResponseData<?>> update(@Valid @RequestBody ForeignLangUpdateDTO dto) {
        ResponseData<?> update = service.update(dto.getForeign_language_id(), dto);
        if (update.getSuccess())
            return ResponseEntity.status(202).body(update);
        return ResponseEntity.status(400).body(update);
    }

    @Operation(summary = "This API is used for delete Foreign Language", responses = {
            @ApiResponse(responseCode = "202", description = "Foreign Language Deleted", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @DeleteMapping(BASIC_DELETE_BY_ID_URL)
    public ResponseEntity<ResponseData<?>> delete(@PathVariable Integer deletedID) {
        ResponseData<?> delete = service.delete(deletedID);
        if (delete.getSuccess())
            return ResponseEntity.status(202).body(delete);
        return ResponseEntity.status(400).body(delete);
    }

    @Operation(summary = "This API is used for get by id Foreign Language", responses = {
            @ApiResponse(responseCode = "200", description = "Foreign Language GET by ID", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_BY_ID_URL)
    public ResponseEntity<ResponseData<?>> getByID(@PathVariable Integer getID) {
        ResponseData<?> body = service.get(getID);
        if (body.getSuccess())
            return ResponseEntity.ok(body);
        return ResponseEntity.badRequest().body(body);
    }

    @Operation(summary = "This API is used for get by tConst Foreign Language", responses = {
            @ApiResponse(responseCode = "200", description = "Foreign Language GET by tConst", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_BY_T_CONST_URL)
    public ResponseEntity<ResponseData<?>> getByTConst(@PathVariable String tConst) {
        ResponseData<?> byTConst = service.getByTConst(tConst);
        if (byTConst.getSuccess())
            return ResponseEntity.ok(byTConst);
        return ResponseEntity.badRequest().body(byTConst);
    }

    @Operation(summary = "This API is used for get all Foreign Language", responses = {
            @ApiResponse(responseCode = "200", description = "Foreign Language GET All", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_URL)
    public ResponseEntity<ResponseData<?>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "This API is used for get all active Foreign Language", responses = {
            @ApiResponse(responseCode = "200", description = "Foreign Language GET All Active", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_ACTIVE_URL)
    public ResponseEntity<ResponseData<?>> getAllActive() {
        return ResponseEntity.ok(service.getAllActive());
    }

    @Operation(summary = "This API is used for get all by status Foreign Language", responses = {
            @ApiResponse(responseCode = "200", description = "Foreign Language GET All by Status", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_BY_STATUS_ID_URL)
    public ResponseEntity<ResponseData<?>> getAllByStatusID(@PathVariable Integer statusID) {
        ResponseData<?> allByStatusID = service.getAllByStatusID(statusID);
        if (allByStatusID.getSuccess())
            return ResponseEntity.ok(allByStatusID);
        return ResponseEntity.badRequest().body(allByStatusID);
    }

    @Operation(summary = "This API is used for get all by modul Foreign Language", responses = {
            @ApiResponse(responseCode = "200", description = "Foreign Language GET All by Modul", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_BY_MODULE_URL)
    public ResponseEntity<ResponseData<?>> getAllByModulID(@PathVariable Integer modulID) {
        ResponseData<?> allByModuleID = service.getAllByModulID(modulID);
        if (allByModuleID.getSuccess())
            return ResponseEntity.ok(allByModuleID);
        return ResponseEntity.badRequest().body(allByModuleID);
    }


}
