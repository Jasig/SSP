package org.jasig.ssp.transferobject;
 
import java.io.Serializable;
import java.util.UUID;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.StudentDocument;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
 
public class StudentDocumentTO extends AbstractAuditableTO<StudentDocument> implements TransferObject<StudentDocument>, Serializable {
	
	private static final long serialVersionUID = -1660022036026969566L;

    private CommonsMultipartFile file;
    
    private String comment;
    
    private String name;
    
    private UUID confidentialityLevelId;
    
    private String fileName;
    
    private String author;
 
    public StudentDocumentTO() {
    	super();
    }
    public StudentDocumentTO(StudentDocument studentDocument) {
    	this.setComment(studentDocument.getComments());
    	this.setConfidentialityLevelId(studentDocument.getConfidentialityLevel().getId());
    	this.setFileName(studentDocument.getFileName());
    	this.setName(studentDocument.getName());
    	this.setAuthor(studentDocument.getAuthor().getFullName());
	}

	public CommonsMultipartFile getFile() {
        return file;
    }
 
    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

	public UUID getConfidentialityLevelId() {
		return confidentialityLevelId;
	}

	public void setConfidentialityLevelId(UUID confidentialityLevelId) {
		this.confidentialityLevelId = confidentialityLevelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}