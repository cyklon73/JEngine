package de.cyklon.jengine.gameobject;

public interface IObjectController {

    void onCollisionEnter(Collision collision);
    void onCollisionStay(Collision collision);
    void onCollisionExit(Collision collision);


}
