package com.coreteam.languageservice.controller.modul;

import com.coreteam.languageservice.controller.BaseController;
import com.coreteam.languageservice.dto.modul.ModulCreateDTO;
import com.coreteam.languageservice.dto.modul.ModulUpdateDTO;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.modul.ModulService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.coreteam.languageservice.controller.BaseURL.*;

@RestController
@RequestMapping(MODUL_URL)
@Tag(name = "Module", description = "This API is used for Module")
public class ModulController extends BaseController<ModulService> {


    public ModulController(ModulService service) {
        super(service);
    }

    @Operation(summary = "This API is used for create Modul", responses = {
            @ApiResponse(responseCode = "201", description = "Modul Created", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @PostMapping(BASIC_CREATE_URL)
    public ResponseEntity<ResponseData<?>> create(@RequestBody @Valid ModulCreateDTO dto) {
        ResponseData<?> body = service.create(dto);
        if (body.getSuccess())
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @Operation(summary = "This API is used for update Modul", responses = {
            @ApiResponse(responseCode = "202", description = "Modul Updated", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @PutMapping(BASIC_UPDATE_URL)
    public ResponseEntity<ResponseData<?>> update(@RequestBody @Valid ModulUpdateDTO dto) {
        ResponseData<?> update = service.update(dto.getModul_id(), dto);
        if (update.getSuccess())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(update);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(update);
    }

    @Operation(summary = "This API is used for delete Modul", responses = {
            @ApiResponse(responseCode = "202", description = "Modul Deleted", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @DeleteMapping(BASIC_DELETE_BY_ID_URL)
    public ResponseEntity<ResponseData<?>> delete(@PathVariable Integer deletedID) {
        ResponseData<?> delete = service.delete(deletedID);
        if (delete.getSuccess())
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(delete);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(delete);
    }

    @Operation(summary = "This API is used for get Modul", responses = {
            @ApiResponse(responseCode = "200", description = "Modul Get", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_BY_ID_URL)
    public ResponseEntity<ResponseData<?>> getByID(@PathVariable Integer getID) {
        ResponseData<?> body = service.get(getID);
        if (body.getSuccess())
            return ResponseEntity.status(HttpStatus.OK).body(body);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

    }

    @Operation(summary = "This API is used for get all Modul", responses = {
            @ApiResponse(responseCode = "200", description = "Modul GET All", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_URL)
    public ResponseEntity<ResponseData<?>> getAll() {
        ResponseData<?> all = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    @Operation(summary = "This API is used for get all active Modul", responses = {
            @ApiResponse(responseCode = "200", description = "Modul GET All Active", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_ACTIVE_URL)
    public ResponseEntity<ResponseData<?>> getAllActive() {
        return ResponseEntity.ok(service.getAllActive());
    }

    @Operation(summary = "This API is used for get all status Modul", responses = {
            @ApiResponse(responseCode = "200", description = "Modul GET All by status", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseData.class)))})
    @GetMapping(BASIC_GET_ALL_BY_STATUS_ID_URL)
    public ResponseEntity<ResponseData<?>> getAllByStatusID(@PathVariable Integer statusID) {
        ResponseData<?> allByStatusID = service.getAllByStatusID(statusID);
        if (allByStatusID.getSuccess())
            return ResponseEntity.ok(allByStatusID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allByStatusID);
    }

}
