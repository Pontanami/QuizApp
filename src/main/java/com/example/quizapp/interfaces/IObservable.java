package com.example.quizapp.interfaces;

public interface IObservable {
    void subscribe(IObserver observer);
    void unsubscribe(IObserver observer);
    void notifySubscribers();
}
