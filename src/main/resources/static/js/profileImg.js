fetch('http://localhost:8080/api/loggedUserPhoto')
    .then((response) => response.json())
    .then((data) => {
        let imageLocation = data;
        console.log(data);
        const span = document.querySelector("#navbarDropdownMenuAvatar");
        var html = `<img class="rounded-circle" height="25" alt=""  loading="lazy"
                        />`
        if (data !== null || data !== ""){
            html = `<img th:src="@{${imageLocation}}" class="rounded-circle" height="25" alt=""  loading="lazy"
                        />`;
            console.log(imageLocation)
        }

        span.insertAdjacentHTML("beforeend", html)


    });