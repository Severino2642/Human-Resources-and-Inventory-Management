function nextQuestion(current) {
    const currentQuestion = document.getElementById(`q${current}`);
    const input = currentQuestion.querySelector("input, select");

    if (!input.checkValidity()) {
        alert("Veuillez remplir cette question correctement.");
        return;
    }

    currentQuestion.style.animation = "slideGauche 0.2s ease-in";
    currentQuestion.classList.remove("active");
    const nextQuestion = document.getElementById(`q${current + 1}`);
    if (nextQuestion) {
        nextQuestion.classList.add("active");
        nextQuestion.style.animation = "slideDroite 0.2s ease-in";
    }
}

function validateFinalForm() {
    const form = document.getElementById("questionnaire");

    if (!form.checkValidity()) {
        alert("Veuillez remplir toutes les questions.");
        return false;
    }
    alert("Formulaire soumis avec succes !");
    return true;
}

function nextSlide(current) {
    var listSousComponents = document.querySelectorAll(".sous-components");
    var listBtNavigation = document.querySelectorAll(".bt-nav");
    for (let i = 0; i < listSousComponents.length; i++) {
        listSousComponents[i].classList.remove("active");
    }
    for (let i = 0; i < listBtNavigation.length; i++) {
        listBtNavigation[i].classList.remove("active");
    }

    var currentComponent = document.getElementById(`s${current}`);
    var currentBtNavigation = document.getElementById(`bt${current}`);
    if (currentComponent) {
        currentComponent.classList.add("active");
        currentComponent.style.animation = "slideDroite 0.2s ease-in";
    }
    if (currentBtNavigation) {
        currentBtNavigation.classList.add("active");
    }
}