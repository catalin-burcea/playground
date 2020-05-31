package ro.cburcea.playground.designpatterns.state.mp3player;

public class StandbyState implements State {

    public void pressPlay(MP3PlayerContext context) {
        context.setState(new PlayingState());
    }

}
