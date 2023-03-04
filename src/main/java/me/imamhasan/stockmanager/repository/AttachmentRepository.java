package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    /**
     * Retrieves all attachments associated with the given module.
     * @param moduleId The ID of the module.
     * @return A list of attachments associated with the module.
     */
    List<Attachment> findByModuleId(Long moduleId);

    /**
     * Retrieves all attachments of the given type associated with the given module.
     * @param moduleId The ID of the module.
     * @param type The type of the attachment.
     * @return A list of attachments of the given type associated with the module.
     */
    List<Attachment> findByModuleIdAndType(Long moduleId, Attachment.AttachmentType type);

    /**
     * Retrieves all attachments associated with the given file path.
     * @param filePath The file path.
     * @return A list of attachments associated with the file path.
     */
    @Query("SELECT a FROM Attachment a JOIN a.filePaths f WHERE f = :filePath")
    List<Attachment> findByFilePath(String filePath);

    /**
     * Deletes all attachments associated with the given module.
     * @param moduleId The ID of the module.
     */
    void deleteByModuleId(Long moduleId);

    /**
     * Deletes all attachments of the given type associated with the given module.
     * @param moduleId The ID of the module.
     * @param type The type of the attachment.
     */
    void deleteByModuleIdAndType(Long moduleId, Attachment.AttachmentType type);


}