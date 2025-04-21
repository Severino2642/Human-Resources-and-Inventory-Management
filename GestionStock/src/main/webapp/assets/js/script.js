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
