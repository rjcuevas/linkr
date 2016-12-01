package com.linkr.url.dao;

import com.linkr.message.LinkrAppException;
import com.linkr.url.domain.ShortUrl;
import com.linkr.url.dto.ShortenUrlDTO;

public interface SUrlDAO {
	
	
	ShortUrl getGenShortUrl(ShortenUrlDTO shortenUrlDTO) throws LinkrAppException;
	
}
