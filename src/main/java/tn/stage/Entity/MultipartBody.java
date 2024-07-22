package tn.stage.Entity;

import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.util.List;

public class MultipartBody {

    @FormParam("files")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public List<InputPart> files;
}
