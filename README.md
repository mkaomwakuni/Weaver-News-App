<h1 align="left">Weaver News App</h1>

<p align="left">
  <b>Stay Informed with the Global Latest News</b>
</p>

<table>
  <tr>
    <td>
      <p align="left">
        <a href="#key-features">Key Features</a> •
        <a href="#technologies">Technologies</a> •
        <a href="#installation">Installation</a> •
        <a href="#usage">Usage</a> •
        <a href="#api-integration">API Integration</a> •
        <a href="#screenshots">Screenshots</a> •
        <a href="#contributing">Contributing</a> •
        <a href="#license">License</a>
      </p>
    </td>
  </tr>
  <tr>
    <td>
      <p align="left">
        <b>Screenshots</b>
      </p>
    </td>
  </tr>
  <tr>
    <td>
      <table>
        <tr>
          <td align="center">
            <img src="https://private-user-images.githubusercontent.com/61048381/302991671-3d35ef50-c51d-4d9f-9565-74a51ede4000.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDczMTIwMzMsIm5iZiI6MTcwNzMxMTczMywicGF0aCI6Ii82MTA0ODM4MS8zMDI5OTE2NzEtM2QzNWVmNTAtYzUxZC00ZDlmLTk1NjUtNzRhNTFlZGU0MDAwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDclMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjA3VDEzMTUzM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWZlNzNjNWQyNTgwY2ZlYWM1NDQ2NzEzNTNhMzJiZDRlZThjYjBlNTQ1MTI1NWIwNzVjZjEwODBhY2YzZjExMGYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.h5YC6zw50CyIFnfWsjxh_LLNGO0J7yfCTfGzHubBw70" width="350" alt="Screenshot 1">
          </td>
          <td align="center">
            <img src="https://private-user-images.githubusercontent.com/61048381/302986795-f9baddb9-209a-4ba6-b5a4-4965ed172e54.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDczMTE0ODUsIm5iZiI6MTcwNzMxMTE4NSwicGF0aCI6Ii82MTA0ODM4MS8zMDI5ODY3OTUtZjliYWRkYjktMjA5YS00YmE2LWI1YTQtNDk2NWVkMTcyZTU0LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDclMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjA3VDEzMDYyNVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTMwZDhjMmE4YmFjNDk5ZjE0YTVjOTEzZGY4NzcyNTdmOThmNzU4YTU2YmQ0MTlkYzA0NWE1MTFmNmFjMTg1OGImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.hTZ4DCZ2_kUrqVCS2ylwFWlYwVTnw06ODDLM8Zd2NUc" width="350" alt="Screenshot 2">
          </td>
        </tr>
        <tr>
          <td align="center">
            <img src="https://private-user-images.githubusercontent.com/61048381/302986817-44599f74-bf33-4f8b-a5da-9a18662f9970.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDczMTE0ODUsIm5iZiI6MTcwNzMxMTE4NSwicGF0aCI6Ii82MTA0ODM4MS8zMDI5ODY4MTctNDQ1OTlmNzQtYmYzMy00ZjhiLWE1ZGEtOWExODY2MmY5OTcwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDclMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjA3VDEzMDYyNVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTg2MGRiMGEyOTQwMjkzMmQ5YmZiNGJjOTQ5YzNmYTMzMWNkNzRlNWNjYjQ1ZmQ3ZDZjMWY2Yjg2YTg5MDY3OGMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.JWdUQty7gKoC2Ceq4QIwH6RPs4ThSXCJiNrXUMIJOdY" width="350" alt="Screenshot 3">
          </td>
          <td align="center">
            <img src="https://private-user-images.githubusercontent.com/61048381/302986835-5a0b873b-c44b-4ff5-9fee-b5116cdda31a.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDczMTE0ODUsIm5iZiI6MTcwNzMxMTE4NSwicGF0aCI6Ii82MTA0ODM4MS8zMDI5ODY4MzUtNWEwYjg3M2ItYzQ0Yi00ZmY1LTlmZWUtYjUxMTZjZGRhMzFhLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDclMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjA3VDEzMDYyNVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWY4MGE2ZTI3MDkzMjkwNGJkMTE2YzgwYjhlNWFiMzNkNjkzNGE4NjZlN2I5MjUyY2FjZDNmYWU4MzBmN2RmZDgmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.Vru3kewgk-6C9m4ONm0SSIdsJwT8cnwRyjzr90cf1LM" width="350" alt="Screenshot 4">
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <p align="left"> 
        <b>Key Features</b>
      </p>
    </td>
  </tr>
  <tr>
    <td>
      <p align="left">
        - Browse and read the latest news articles from various trusted sources.<br>
        - Filter news by categories such as technology, sports, entertainment, and more.<br>
        - Save your favorite articles for offline reading.<br>
        - Intuitive and user-friendly interface for a seamless news reading experience.
      </p>
    </td>
  </tr>
  <tr>
    <td>
      <p align="left">
        <b>Technologies</b>
      </p>
    </td>
  </tr>
  <tr>
    <td>
      <p align="left">
        - <a href="https://github.com/JetBrains/kotlin">Kotlin</a>: Modern programming language for Android app development.<br>
        - <a href="https://github.com/android/compose">Jetpack Compose</a>: Declarative UI toolkit for building engaging user interfaces.<br>
        - <a href="https://github.com/square/retrofit">Retrofit</a>: HTTP client for making API requests.<br>
        - <a href="https://github.com/bumptech/glide">Glide</a>: Image loading library for smooth image rendering.<br>
        - <a href="https://github.com/android/architecture-components-samples/tree/main/RoomSample">Room</a>: Local database for efficient data storage.<br>
        - <a href="https://github.com">Your Additional Technology</a>: Description.
      </p>
    </td>
  </tr>
  <!-- Add more sections as needed -->
</table>
