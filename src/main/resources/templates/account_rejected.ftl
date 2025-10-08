    <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Rejected - Online Voting System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 500px;
            width: 100%;
        }
        .rejection-icon {
            font-size: 60px;
            color: #dc3545;
            margin-bottom: 20px;
        }
        h1 {
            color: #dc3545;
            margin-bottom: 20px;
        }
        .message {
            color: #666;
            margin-bottom: 30px;
            line-height: 1.6;
        }
        .reason {
            background-color: #f8f9fa;
            border-left: 4px solid #dc3545;
            padding: 15px;
            margin: 20px 0;
            text-align: left;
        }
        .actions {
            margin-top: 30px;
        }
        .btn {
            display: inline-block;
            padding: 12px 24px;
            margin: 0 10px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="rejection-icon">❌</div>
        <h1>Account Rejected</h1>
        
        <div class="message">
            <p>We regret to inform you that your account registration has been rejected.</p>
            <p>Your application did not meet our verification requirements at this time.</p>
        </div>

        <#if rejectionReason??>
            <div class="reason">
                <strong>Reason for rejection:</strong><br>
                ${rejectionReason}
            </div>
        </#if>

        <div class="message">
            <p>You may reapply for an account by ensuring all required documents and information are provided correctly.</p>
        </div>

        <div class="actions">
            <a href="/register" class="btn btn-primary">Apply Again</a>
            <a href="/login" class="btn btn-secondary">Back to Login</a>
        </div>
    </div>
</body>
</html>