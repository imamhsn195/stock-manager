package me.imamhasan.stockmanager.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.imamhasan.stockmanager.config.AttachmentType;

import javax.persistence.*;
import java.util.Set;

    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Data
    @Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttachmentType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = true)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = true)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = true)
    private Purchase purchase;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "attachment_file_paths", joinColumns = @JoinColumn(name = "attachment_id"))
    @Column(name = "file_path", nullable = false)
    private Set<String> filePaths;

}
