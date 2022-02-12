package ro.cburcea.playground.caching.ignite;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.apache.ignite.resources.IgniteInstanceResource;

@Slf4j
public class IgniteLifecycleBean implements LifecycleBean {
    @IgniteInstanceResource
    public Ignite ignite;

    @Override
    public void onLifecycleEvent(LifecycleEventType evt) {
        if (evt == LifecycleEventType.BEFORE_NODE_START) {
            log.info("Before the node starts.\n");
        }
        if (evt == LifecycleEventType.AFTER_NODE_START) {
            log.info("After the client node starts. Cluster state = {} \n", ignite.cluster().state());
        }
        if (evt == LifecycleEventType.BEFORE_NODE_STOP) {
            log.info("Before the node stops.\n");
        }
        if (evt == LifecycleEventType.AFTER_NODE_STOP) {
            log.info("After the node stops.\n");
        }
    }
}