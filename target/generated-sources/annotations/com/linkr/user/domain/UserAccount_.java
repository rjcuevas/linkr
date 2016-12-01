package com.linkr.user.domain;

import com.linkr.url.domain.LinkrUrl;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserAccount.class)
public abstract class UserAccount_ {

	public static volatile SingularAttribute<UserAccount, Long> userID;
	public static volatile SingularAttribute<UserAccount, String> username;
	public static volatile SingularAttribute<UserAccount, String> password;
	public static volatile SetAttribute<UserAccount, LinkrUrl> linkUrls;

}

