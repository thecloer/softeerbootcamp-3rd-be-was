"use strict"

{
    const $form = document.getElementById("form");
    const $message = document.getElementById("form-message");

    const redirectByPath = (path) => window.location.href = window.origin + path;
    const formDataToUrl = (formData) => {
        const url = new URL("/user/create", window.location.origin);
        for (const [key, value] of formData.entries())
            url.searchParams.append(key, value);
        return url;
    }

    const formSubmitHandler = async (event) => {
        event.preventDefault();
        const formData = new FormData($form);
        const url = formDataToUrl(formData);

        const response = await fetch(url, {method: "GET"});

        if (response.ok) {
            const redirectPath = response.headers.get("Location");
            if (redirectPath)
                redirectByPath(redirectPath);
        }

        const data = await response.json();
        if (data.message) {
            $message.innerText = data.message;
            $message.classList.remove("hidden");
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