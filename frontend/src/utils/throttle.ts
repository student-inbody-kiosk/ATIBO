function throttle(callback, delay) {
    let timer;
    return (event) => {
        if (timer) return;
        timer = setTimeout(
            () => {
                callback(event);
                timer = null;
            },
            delay,
            event
        );
    };
}

export default throttle;
