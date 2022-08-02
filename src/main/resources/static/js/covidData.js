fetch('http://localhost:8080/api/covidInBg')
    .then((response) => response.json())
    .then((data) => {
        let newCasesBg = data.lastDayCases;
        const dateOfStatistic = new Date(data.date).toLocaleDateString('en-GB');
        let allCases = data.allCases;

        const span = document.querySelector("#newCasesInBg");
        const html = `
                <p class="text-primary fs-1 font-weight-bold  m-0 text-center">${newCasesBg}</p>
                <p class="text-light bg-primary fs-5 m-0 text-center">NEW CASES IN BULGARIA</p>
                <p class="text-dark bg-warning fs-12 text-center">${dateOfStatistic}</p>`;
        span.insertAdjacentHTML("afterbegin", html)

        let allNewCases = data.allCases;

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
        let newCases = data.lastDayCases;
        const dateOfStatistic = new Date(data.date).toLocaleDateString('en-GB');


        const span = document.querySelector("#newCasesInLab");
        const html = `
                <p class="text-primary fs-1 font-weight-bold  m-0 text-center">${newCases}</p>
                <p class="text-light bg-primary fs-5 m-0 text-center">NEW CASES IN CLINIC</p>
                <p class="text-dark bg-warning fs-12 text-center">${dateOfStatistic}</p>`;
        span.insertAdjacentHTML("afterbegin", html)

        let allCases = data.labCases;

        const secondSpan = document.querySelector("#allCasesInLab");
        const secondHtml = `
               <p class="text-primary fs-1 font-weight-bold  m-0 text-center">${allCases}</p>
               <p class="text-light bg-primary fs-5 m-0 text-center">TOTAL CASES IN CLINIC</p>
               <p class="text-dark bg-warning fs-12 text-center">${dateOfStatistic}</p>`;
        secondSpan.insertAdjacentHTML("afterbegin", secondHtml);
    });