package me.imamhasan.stockmanager.service;

import me.imamhasan.stockmanager.model.Attachment;
import me.imamhasan.stockmanager.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentPathRepository attachmentPathRepository;

    @Override
    public Attachment createAttachment(Attachment attachment) {
        Attachment savedAttachment = attachmentRepository.save(attachment);

        for (String path : attachment.getFilePaths()) {
            AttachmentPath attachmentPath = new AttachmentPath();
            attachmentPath.setAttachmentId(savedAttachment.getId());
            attachmentPath.setFilePath(path);
            attachmentPathRepository.save(attachmentPath);
        }

        return savedAttachment;
    }

    @Override
    public Attachment getAttachmentById(Long id) {
        return attachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Attachment not found"));
    }

    @Override
    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    @Override
    public List<String> getAttachmentPathsById(Long id) {
        List<AttachmentPath> attachmentPaths = attachmentPathRepository.findByAttachmentId(id);
        List<String> filePaths = new ArrayList<>();

        for (AttachmentPath attachmentPath : attachmentPaths) {
            filePaths.add(attachmentPath.getFilePath());
        }

        return filePaths;
    }

    @Override
    public Attachment updateAttachment(Long id, Attachment attachment) {
        Attachment existingAttachment = getAttachmentById(id);
        existingAttachment.setModuleId(attachment.getModuleId());
        existingAttachment.setModuleName(attachment.getModuleName());
        existingAttachment.setAttachmentType(attachment.getAttachmentType());

        attachmentRepository.save(existingAttachment);

        List<String> existingFilePaths = getAttachmentPathsById(id);

        // Delete any file paths that no longer exist
        for (String existingFilePath : existingFilePaths) {
            if (!attachment.getFilePaths().contains(existingFilePath)) {
                attachmentPathRepository.deleteByAttachmentIdAndFilePath(id, existingFilePath);
            }
        }

        // Add any new file paths
        for (String newFilePath : attachment.getFilePaths()) {
            if (!existingFilePaths.contains(newFilePath)) {
                AttachmentPath attachmentPath = new AttachmentPath();
                attachmentPath.setAttachmentId(id);
                attachmentPath.setFilePath(newFilePath);
                attachmentPathRepository.save(attachmentPath);
            }
        }

        return existingAttachment;
    }

    @Override
    public void deleteAttachment(Long id) {
        attachmentRepository.deleteById(id);
        attachmentPathRepository.deleteByAttachmentId(id);
    }
}
