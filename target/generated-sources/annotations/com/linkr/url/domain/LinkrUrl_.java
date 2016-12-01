package com.linkr.url.domain;

import com.linkr.user.domain.UserAccount;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LinkrUrl.class)
public abstract class LinkrUrl_ {

	public static volatile SingularAttribute<LinkrUrl, Long> urlID;
	public static volatile SingularAttribute<LinkrUrl, Boolean> public;
	public static volatile SingularAttribute<LinkrUrl, UserAccount> user;
	public static volatile SetAttribute<LinkrUrl, ShortUrl> shortUrlSet;
	public static volatile SingularAttribute<LinkrUrl, String> urlAddress;

}

