package ch.cyberduck.core.dav;

/*
 * Copyright (c) 2002-2013 David Kocher. All rights reserved.
 * http://cyberduck.ch/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Bug fixes, suggestions and comments should be sent to feedback@cyberduck.ch
 */

import ch.cyberduck.core.DefaultIOExceptionMappingService;
import ch.cyberduck.core.Path;
import ch.cyberduck.core.exception.BackgroundException;
import ch.cyberduck.core.features.Read;
import ch.cyberduck.core.transfer.TransferStatus;

import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.github.sardine.impl.SardineException;

/**
 * @version $Id$
 */
public class DAVReadFeature implements Read {

    private DAVSession session;

    public DAVReadFeature(final DAVSession session) {
        this.session = session;
    }

    @Override
    public InputStream read(final Path file, final TransferStatus status) throws BackgroundException {
        Map<String, String> headers = new HashMap<String, String>();
        if(status.isAppend()) {
            headers.put(HttpHeaders.RANGE, "bytes=" + status.getCurrent() + "-");
        }
        try {
            return session.getClient().get(new DAVPathEncoder().encode(file), headers);
        }
        catch(SardineException e) {
            throw new DAVExceptionMappingService().map("Download failed", e, file);
        }
        catch(IOException e) {
            throw new DefaultIOExceptionMappingService().map("Download failed", e, file);
        }
    }

    @Override
    public boolean append(final Path file) {
        return true;
    }
}
