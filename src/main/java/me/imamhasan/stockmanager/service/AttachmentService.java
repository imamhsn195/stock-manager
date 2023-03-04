package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Attachment;

public interface AttachmentService {
    Attachment saveAttachment(Attachment attachment);
    Attachment getAttachmentById(Long attachmentId);
    Attachment updateAttachment(Long attachmentId, Attachment updatedAttachment);
    void deleteAttachment(Long attachmentId);
}