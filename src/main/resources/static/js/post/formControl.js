"use strict"

{
    const $form = document.getElementById("form");
    const $message = document.getElementById("form-message");

    const PATH = $form.action;

    const responseHandler = async (response) => {
        if (!response.ok) {
            const data = await response.json();
            if (data.message) {
                $message.innerText = data.message;
                $message.classList.remove("hidden");
            }
            return;
        }

        if (response.redirected) {
            window.location.href = response.url;
            return;
        }

        const redirectPath = response.headers.get("Location");
        if (redirectPath) {
            window.location.href = window.location.origin + redirectPath;
            return;
        }
    };

    const formSubmitHandler = async (event) => {
        event.preventDefault();
        const formData = new FormData($form);

        const response = await fetch(PATH, {
            method: "POST",
            body: formData,
        });

        responseHandler(response);
    };

    const hideMessage = () => {
        if (!$message.classList.contains("hidden"))
            $message.classList.add("hidden");
    };

    $form.addEventListener("submit", formSubmitHandler);
    for (const $input of $form.querySelectorAll("input"))
        $input.addEventListener("focus", hideMessage);
}