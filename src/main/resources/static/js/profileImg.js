fetch('http://localhost:8080/api/loggedUserPhoto')
    .then((response) => response.json())
    .then((data) => {
        let imageLocation = data.imageAddress;
        console.log(imageLocation);
        const span = document.querySelector("#navbarDropdownMenuAvatarUser");
        /*var html = `<img class="rounded-circle" height="25" alt=""  loading="lazy"
                        />`
        if (data !== null || data !== ""){*/
            const html = `<img src="${imageLocation}" class="rounded-circle" height="25" alt=""  loading="lazy"
                        />`;
        /*}*/

        span.insertAdjacentHTML("beforeend", html)


    });