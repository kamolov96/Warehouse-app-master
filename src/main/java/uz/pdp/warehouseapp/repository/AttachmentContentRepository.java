package uz.pdp.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouseapp.entity.Attachment;
import uz.pdp.warehouseapp.entity.AttachmentContent;

import java.util.UUID;

@Repository
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
AttachmentContent findByAttachment(Attachment attachment);
}
