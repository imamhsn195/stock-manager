package me.imamhasan.stockmanager.controller;

import io.swagger.annotations.ApiOperation;
import me.imamhasan.stockmanager.model.Attachment;
import me.imamhasan.stockmanager.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/{id}")
    @ApiOperation("Get an attachment by ID")
    public ResponseEntity<Attachment> getAttachment(@PathVariable Long id) {
        Attachment attachment = attachmentService.getAttachment(id);
        if (attachment != null) {
            return ResponseEntity.ok(attachment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/module/{moduleId}")
    @ApiOperation("Get all attachments associated with a module")
    public ResponseEntity<List<Attachment>> getAttachmentsByModuleId(@PathVariable Long moduleId) {
        List<Attachment> attachments = attachmentService.getAttachmentsByModuleId(moduleId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/module/{moduleId}/type/{type}")
    @ApiOperation("Get all attachments of a specific type associated with a module")
    public ResponseEntity<List<Attachment>> getAttachmentsByModuleIdAndType(
            @PathVariable Long moduleId, @PathVariable Attachment.AttachmentType type) {
        List<Attachment> attachments = attachmentService.getAttachmentsByModuleIdAndType(moduleId, type);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/file/{filePath}")
    @ApiOperation("Get all attachments associated with a file path")
    public ResponseEntity<List<Attachment>> getAttachmentsByFilePath(@PathVariable String filePath) {
        List<Attachment> attachments = attachmentService.getAttachmentsByFilePath(filePath);
        return ResponseEntity.ok(attachments);
    }

    @PostMapping
    @ApiOperation("Create a new attachment")
    public ResponseEntity<Attachment> createAttachment(@RequestBody Attachment attachment) {
        Attachment createdAttachment = attachmentService.createAttachment(attachment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttachment);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update an existing attachment")
    public ResponseEntity<Attachment> updateAttachment(@PathVariable Long id, @RequestBody Attachment attachment) {
        Attachment updatedAttachment = attachmentService.updateAttachment(id, attachment);
        if (updatedAttachment != null) {
            return ResponseEntity.ok(updatedAttachment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete an attachment")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        boolean success = attachmentService.deleteAttachment(id);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
