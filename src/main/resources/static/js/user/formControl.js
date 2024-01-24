"use strict"

{
    const $form = document.getElementById("form");
    const $message = document.getElementById("form-message");

    const PATH = $form.action;
    const redirectByPath = (path) => window.location.href = window.origin + path;

    const formSubmitHandler = async (event) => {
        event.preventDefault();
        const formData = new FormData($form);

        const response = await fetch(PATH, {
            method: "POST",
            body: JSON.stringify(Object.fromEntries(formData.entries())),
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const redirectPath = response.headers.get("Location");
            if (redirectPath)
                redirectByPath(redirectPath);
        }
        else {
            const data = await response.json();
            if (data.message) {
                $message.innerText = data.message;
                $message.classList.remove("hidden");
            }
        }
    };

    const hideMessage = () => {
        if (!$message.classList.contains("hidden"))
            $message.classList.add("hidden");
    };

    $form.addEventListener("submit", formSubmitHandler);
    for (const $input of $form.querySelectorAll("input"))
        $input.addEventListener("focus", hideMessage);
}