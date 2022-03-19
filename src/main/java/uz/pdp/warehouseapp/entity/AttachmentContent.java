package uz.pdp.warehouseapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class AttachmentContent {
    @Id
    private UUID id=UUID.randomUUID();
    private byte[] bytes;
    @OneToOne
    private Attachment attachment;

    public AttachmentContent(byte[] bytes, Attachment attachment) {
        this.bytes = bytes;
        this.attachment = attachment;
    }
}
