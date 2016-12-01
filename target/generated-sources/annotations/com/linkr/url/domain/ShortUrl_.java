package com.linkr.url.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ShortUrl.class)
public abstract class ShortUrl_ {

	public static volatile SingularAttribute<ShortUrl, Long> shortUrlID;
	public static volatile SingularAttribute<ShortUrl, Long> srcLookUpID;
	public static volatile SingularAttribute<ShortUrl, String> shortURLAddress;
	public static volatile SingularAttribute<ShortUrl, LinkrUrl> linkrUrl;

}

