<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Rule Engine</title>
    <style>
        /* Basic CSS for styling */
        body { font-family: Arial, sans-serif; }
        #rule-form, #eval-form { margin: 20px; padding: 10px; border: 1px solid #ccc; }
        label, input { display: block; margin: 5px 0; }
        button { margin-top: 10px; }
    </style>
</head>
<body>
    <h2>Rule Engine</h2>
    <div id="rule-form">
        <h3>Create Rule</h3>
        <form method="POST" action="/api/rules/create">
            <label>Rule String:</label>
            <input type="text" name="ruleString" required>
            <button type="submit">Create Rule</button>
        </form>
    </div>
    <div id="eval-form">
        <h3>Evaluate Rule</h3>
        <form id="evaluationForm" onsubmit="evaluateRule(event)">
            <label>Age:</label>
            <input type="number" name="age" required>
            <label>Department:</label>
            <input type="text" name="department" required>
            <label>Salary:</label>
            <input type="number" name="salary">
            <label>Experience:</label>
            <input type="number" name="experience">
            <button type="submit">Evaluate Rule</button>
        </form>
        <div id="result"></div> <!-- Section to display result -->
    </div>
    <script>
        // JS function to handle rule evaluation
        function evaluateRule(event) {
            event.preventDefault(); // Prevent the default form submission

            const formData = new FormData(document.getElementById('evaluationForm'));
            const data = {};
            formData.forEach((value, key) => {
                data[key] = value;
            });
            console.log(data);

            fetch('/api/rules/evaluate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(result => {
                document.getElementById('result').innerText = 'Evaluation Result: ' + result;
            })
            .catch(error => {
                document.getElementById('result').innerText = 'Error: ' + error.message;
            });
        }
    </script>
</body>
</html>
