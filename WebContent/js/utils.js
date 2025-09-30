/**
 * Sets focus on the first input element with a given name within a form
 * and applies an 'error' class for styling.
 *
 * @param {HTMLFormElement} formElement The form to search within.
 * @param {string} inputName The name attribute of the input to focus on.
 */
function focusOnError(formElement, inputName) {
    const element = formElement.querySelector(`[name="${inputName}"]`);
    if (element) {
        // Add a class for styling the error state
        element.classList.add('error-field');
        element.focus();

        // Remove the error class when the user starts typing
        element.addEventListener('input', () => {
            element.classList.remove('error-field');
        }, { once: true });
    }
}

function updateDays() {
    let yearSelect = document.getElementById("year");
    let monthSelect = document.getElementById("month");
    let daySelect = document.getElementById("day");

    let selectedYear = parseInt(yearSelect.value);
    let selectedMonth = parseInt(monthSelect.value);

    const currentYear = parseInt(document.getElementById("currentYear").value);
    const currentMonth = parseInt(document.getElementById("currentMonth").value);
    const currentDay = parseInt(document.getElementById("currentDay").value);

    // Get the reserved day from the hidden input, if it exists
    const reservedDayInput = document.getElementById("reservedDay");
    const reservedDay = reservedDayInput ? parseInt(reservedDayInput.value) : null;

    daySelect.innerHTML = "";

    let daysInMonth = new Date(selectedYear, selectedMonth, 0).getDate();

    let startDay = 1;

    // RESTRICTION: Check if the selected month and year are the current month and year.
    // If so, the available days should start from the current day to enforce "future only".
    if (selectedYear === currentYear && selectedMonth === currentMonth) {
        startDay = currentDay;
    }

    for (let i = startDay; i <= daysInMonth; i++) {
        let option = document.createElement("option");
        option.value = i;
        option.text = i;
        daySelect.appendChild(option);
    }

    // PRIORITIZE: Prioritize setting the value from the reserved data.
    if (reservedDay) {
        // The reserved day is selected, even if it's "in the past" relative to the current day,
        // because we are updating an existing reservation.
        daySelect.value = reservedDay;
    } else {
        // If this is a new selection (e.g., month/year was just changed), select the first available day.
        daySelect.value = startDay;
    }
}

function updateMonths() {
    let yearSelect = document.getElementById("year");
    let selectedYear = parseInt(yearSelect.value);

    // Get the current year and month from hidden fields
    const currentYear = parseInt(document.getElementById("currentYear").value);
    const currentMonth = parseInt(document.getElementById("currentMonth").value);

    let monthSelect = document.getElementById("month");
    monthSelect.innerHTML = "";

    const monthNames = ["", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"];

    let startMonth = 1;
    // RESTRICTION: Only restrict the starting month if the selected year is the current year.
    if (selectedYear === currentYear) {
        startMonth = currentMonth;
    }

    for (let i = startMonth; i <= 12; i++) {
        let option = document.createElement("option");
        option.value = i;
        option.text = monthNames[i];
        monthSelect.appendChild(option);
    }

    // Check if a reserved month is available from the hidden field
    const reservedMonthInput = document.getElementById("reservedMonth");
    const reservedMonth = reservedMonthInput ? parseInt(reservedMonthInput.value) : null;

    if (reservedMonth) {
        // ALWAYS select the reserved month if it exists.
        monthSelect.value = reservedMonth;
    } else if (selectedYear === currentYear) {
        // If no reserved month, default to the current month if the current year is selected.
        monthSelect.value = currentMonth;
    } else {
        // Otherwise, default to the first month (January) for next year
        monthSelect.value = 1;
    }

    // Trigger the day update after months are updated
    updateDays();
}
