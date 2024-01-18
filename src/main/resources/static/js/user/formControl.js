{
    const $form = document.getElementById("form");
    const $message = document.getElementById("form-message");

    const message = new URLSearchParams(window.location.search).get("message");
    if (message) {
        $message.innerText = message;
        $message.classList.remove("hidden");
    }

    for(const $input of $form.querySelectorAll("input")) {
        $input.addEventListener("focus", () => {
            if(!$message.classList.contains("hidden"))
                $message.classList.add("hidden");
        }, { once: true});
    }
}