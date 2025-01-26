<script>
    import { weeks} from "../stores.js"

    let weekID = 1;
    let problem = false;
    let title;
    let description;
    let errorText = "Bitte füllen Sie die oben stehenden Felder aus.";

    let weeksForIdCheck;
    weeks.subscribe((value) => {
		weeksForIdCheck = value;
	});
    function parameterValid(){
        if (weekID < 1 || weekID > 32) {
            errorText = "Fehlerhafte WeekID. ID muss zwischen 1 und 32 sein.";
            return false;
        }
        if (weeksForIdCheck.some((week) => weekID === week.weekID)){
            errorText = "Diese ID ist schon vergeben";
            return false;
        }
        if (title === undefined || title === "") {
            errorText = "Bitte geben Sie einen Titel ein.";
            return false;
        }
        if (description === undefined || description === "") {
            errorText = "Bitte geben Sie eine Beschreibung ein.";
            return false;
        }
        return true;
    }

    function handleSubmit(){
        if (!parameterValid()){
            return;
        }
        weeks.update((weeks) => {
            weeks.push({
                weekID: weekID,
                problem: problem,
                title: title,
                description: description
            });
            console.table(weeks);
            return weeks;
        });
        weekID = weekID+1;
        problem = false;
        title = "";
        description = "";
        errorText = "Der Eintrag wurde erfolgreich hinzugefügt.";
    }

</script>


<div class="container-fluid row bg-dark">
    <div class="p-2"></div>
    <div class="col-sm-3 d-flex justify-content-center flex-column">
        <div class="d-flex flex-row col-sm-12 form-number">
            <label class="p-2 col-sm-4 text-white" for="weekID"><b>Week ID</b></label>
            <input class="p-2 col-sm-2 my-auto" type="number" id="weekID" bind:value={weekID} min=1 max=32><br>
        </div>
        <div class="d-flex flex-row col-sm-12">
            <label class="p-2 col-sm-4 text-white" for="problem"><b>Problem</b></label>
            <input class="p-2 col-sm-8 my-auto form-check-input" type="checkbox" id="problem" bind:checked={problem}><br>
        </div>
        <div class="d-flex flex-row col-sm-12">
            <label class="p-2 col-sm-4 text-white" for="title"><b>Title</b></label>
            <input class="p-2 col-sm-8 my-auto" type="text" id="title" bind:value={title}>
        </div>
    </div>
    <div class="col-sm-6 d-flex justify-content-center flex-column">
        <label class="p-2 text-white" for="description"><b>Beschreibung</b></label><br>
        <textarea rows="3" class="col-sm-8" bind:value={description} id="description"/>
    </div>
    <div class="col-sm-3 d-flex justify-content-center">
        <button class="btn btn-primary col-sm-5 display-4 d-flex justify-content-center" on:click={handleSubmit}><h4 class="display-1">+</h4></button>
    </div>
    <p class="col-sm-12 p-2 text-white">{errorText}</p>
</div>