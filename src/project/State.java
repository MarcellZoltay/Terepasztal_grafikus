package project;

import java.util.*;

/**
 * 
 */
public interface State {

    Status start();

    void setOutput(Status s);
}