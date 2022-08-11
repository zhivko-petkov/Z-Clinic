fetch('http://localhost:8080/api/covidInBg')
    .then((response) => response.json())
    .then((data) => {
        let newCasesBg = commify(data.lastDayCases);
        let date = new Date(data.date);
        date.setDate(date.getDate() + 2);
        const dateOfStatistic = date.toLocaleDateString('en-GB');

        const span = document.querySelector("#newCasesInBg");
        const html = `
                <p class="text-primary fs-1 font-weight-bold  m-0 text-center">${newCasesBg}</p>
                <p class="text-light bg-primary fs-5 m-0 text-center">NEW CASES IN BULGARIA</p>
                <p class="text-dark bg-warning fs-12 text-center">${dateOfStatistic}</p>`;
        span.insertAdjacentHTML("afterbegin", html)

        let allNewCases = commify(data.allCases);

        const secondSpan = document.querySelector("#allCasesInBg");
        const secondHtml = `
               <p class="text-primary fs-1 font-weight-bold  m-0 text-center">${allNewCases}</p>
               <p class="text-light bg-primary fs-5 m-0 text-center">TOTAL CASES IN BULGARIA</p>
               <p class="text-dark bg-warning fs-12 text-center">${dateOfStatistic}</p>`;
        secondSpan.insertAdjacentHTML("afterbegin", secondHtml);
    });

fetch('http://localhost:8080/api/covidInLab')
    .then((response) => response.json())
    .then((data) => {
        let newCases = commify(Number(data.lastDayCases));
        const dateOfStatistic = new Date(data.date).toLocaleDateString('en-GB');


        const span = document.querySelector("#newCasesInLab");
        const html = `
                <p class="text-primary fs-1 font-weight-bold  m-0 text-center">${newCases}</p>
                <p class="text-light bg-primary fs-5 m-0 text-center">NEW CASES IN CLINIC</p>
                <p class="text-dark bg-warning fs-12 text-center">${dateOfStatistic}</p>`;
        span.insertAdjacentHTML("afterbegin", html)

        let allCases = commify(Number(data.labCases));

        const secondSpan = document.querySelector("#allCasesInLab");
        const secondHtml = `
               <p class="text-primary fs-1 font-weight-bold  m-0 text-center">${allCases}</p>
               <p class="text-light bg-primary fs-5 m-0 text-center">TOTAL CASES IN CLINIC</p>
               <p class="text-dark bg-warning fs-12 text-center">${dateOfStatistic}</p>`;
        secondSpan.insertAdjacentHTML("afterbegin", secondHtml);
    });

function redirectMethod(a) {
        location.href = '/news/' + a;
}

function commify(n) {
        var parts = n.toString().split(".");
        const numberPart = parts[0];
        const decimalPart = parts[1];
        const thousands = /\B(?=(\d{3})+(?!\d))/g;
        return numberPart.replace(thousands, " ") + (decimalPart ? "." + decimalPart : "");
}
