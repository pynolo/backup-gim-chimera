package it.giunti.chimera.api05;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.giunti.chimera.model.entity.IdentityService;

@RestController
@CrossOrigin(origins = "*")
public class IdentityController {

	@Autowired
	@Qualifier("identityService")
	private IdentityService identityService;

	@PostMapping("/api05/authenticate")
	public IdentityBean authenticate(@Valid @RequestBody IdentityInputBean input) {

	}

	@PostMapping("/api05/get_identity")
	public IdentityBean getIdentity(@Valid @RequestBody IdentityInputBean input) {

	}
	
	@PostMapping("/api05/get_identity_by_email")
	public IdentityBean getIdentityByEmail(@Valid @RequestBody IdentityInputBean input) {

	}
	
	@PostMapping("/api05/get_identity_by_social_id")
	public IdentityBean getIdentityBySocialId(@Valid @RequestBody IdentityInputBean input) {

	}
	
	@PostMapping("/api05/validate_identity_data")
	public IdentityBean validateIdentityData(@Valid @RequestBody IdentityInputBean input) {

	}
	
	@PostMapping("/api05/create_identity")
	public IdentityBean createIdentity(@Valid @RequestBody IdentityInputBean input) {

	}
	
	@PostMapping("/api05/update_identity")
	public IdentityBean updateIdentity(@Valid @RequestBody IdentityInputBean input) {

	}
	
	@PostMapping("/api05/update_identity_consent")
	public IdentityBean updateIdentityConsent(@Valid @RequestBody IdentityInputBean input) {

	}
	
	@PostMapping("/api05/replace_identity")
	public IdentityBean replaceIdentity(@Valid @RequestBody IdentityInputBean input) {

	}
	
	@PostMapping("/api05/delete_identity")
	public IdentityBean deleteIdentity(@Valid @RequestBody IdentityInputBean input) {

	}
	 
	// Input Beans
	
	public class IdentityInputBean {
		private String accessKey = null;
		private String identityUid = null;
		private String email = null;
		private String password = null;
		private String socialId = null;
		private String redundantIdentityUid = null;
		private String finalIdentityUid = null;
		
		public String getAccessKey() {
			return accessKey;
		}
		public void setAccessKey(String accessKey) {
			this.accessKey = accessKey;
		}
		public String getIdentityUid() {
			return identityUid;
		}
		public void setIdentityUid(String identityUid) {
			this.identityUid = identityUid;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getSocialId() {
			return socialId;
		}
		public void setSocialId(String socialId) {
			this.socialId = socialId;
		}
		public String getRedundantIdentityUid() {
			return redundantIdentityUid;
		}
		public void setRedundantIdentityUid(String redundantIdentityUid) {
			this.redundantIdentityUid = redundantIdentityUid;
		}
		public String getFinalIdentityUid() {
			return finalIdentityUid;
		}
		public void setFinalIdentityUid(String finalIdentityUid) {
			this.finalIdentityUid = finalIdentityUid;
		}
	}
	
}
