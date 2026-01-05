<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>New Election Published</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .email-container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .email-header {
            background-color: #007bff;
            color: white;
            padding: 30px 20px;
            text-align: center;
        }
        .email-header h1 {
            margin: 0;
            font-size: 28px;
        }
        .email-body {
            padding: 30px;
            line-height: 1.6;
            color: #333333;
        }
        .election-details {
            background-color: #f8f9fa;
            border-left: 4px solid #007bff;
            padding: 20px;
            margin: 20px 0;
        }
        .election-details h2 {
            margin-top: 0;
            color: #007bff;
        }
        .detail-item {
            margin: 10px 0;
            font-size: 16px;
        }
        .detail-label {
            font-weight: bold;
            color: #555;
        }
        .important-dates {
            background-color: #fff3cd;
            border: 1px solid #ffeaa7;
            border-radius: 5px;
            padding: 15px;
            margin: 20px 0;
        }
        .cta-button {
            background-color: #28a745;
            color: white;
            padding: 12px 30px;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            margin: 20px 0;
            font-weight: bold;
        }
        .email-footer {
            background-color: #6c757d;
            color: white;
            padding: 20px;
            text-align: center;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <div class="email-header">
            <h1>🗳️ New Election Published!</h1>
        </div>
        
        <div class="email-body">
            <p>Dear <#if voterName??>${voterName}<#else>Voter</#if>,</p>
            
            <p>We are excited to inform you that a new election has been published in your area. Your participation in the democratic process is important to us and your community.</p>
            
            <div class="election-details">
                <h2>${electionName}</h2>
                
                <#if note?? && note?length gt 0>
                <div class="detail-item">
                    <span class="detail-label">Note:</span> ${note}
                </div>
                </#if>
            </div>
            
            <div class="important-dates">
                <h3>📅 Important Dates</h3>
                <div class="detail-item">
                    <span class="detail-label">Election Date:</span> ${electionDate}
                </div>
                <div class="detail-item">
                    <span class="detail-label">Result Date:</span> ${resultDate}
                </div>
            </div>
            
            <p><strong>What's Next?</strong></p>
            <ul>
                <li>Review the election details carefully</li>
                <li>Make note of the important dates</li>
                <li>Prepare to exercise your voting rights</li>
                <li>Stay updated on candidate information</li>
            </ul>
            
            <a href="http://localhost:8080/voter/elections" class="cta-button">View Election Details</a>
            
            <p>Thank you for being an active participant in our democracy. Your vote matters!</p>
            
            <p>Best regards,<br>
            <strong>Online Voting System Team</strong></p>
            
            <hr style="margin: 30px 0; border: none; border-top: 1px solid #eee;">
            
            <p style="font-size: 12px; color: #666;">
                This is an automated email notification. If you have any questions, please contact your local election officer.
            </p>
        </div>
        
        <div class="email-footer">
            <p>&copy; 2026 Online Voting System. All rights reserved.</p>
            <p>Promoting democracy through technology</p>
        </div>
    </div>
</body>
</html>