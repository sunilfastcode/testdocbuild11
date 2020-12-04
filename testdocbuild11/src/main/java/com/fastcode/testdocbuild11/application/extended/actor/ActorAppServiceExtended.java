package com.fastcode.testdocbuild11.application.extended.actor;

import com.fastcode.testdocbuild11.application.core.actor.ActorAppService;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.domain.extended.actor.IActorManagerExtended;
import org.springframework.stereotype.Service;

@Service("actorAppServiceExtended")
public class ActorAppServiceExtended extends ActorAppService implements IActorAppServiceExtended {

    public ActorAppServiceExtended(
        IActorManagerExtended actorManagerExtended,
        IActorMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(actorManagerExtended, mapper, logHelper);
    }
    //Add your custom code here

}
