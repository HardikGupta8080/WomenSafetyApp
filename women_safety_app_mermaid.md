# Women Safety App - Mermaid Diagrams

## Level 0 DFD (Context Diagram)

```mermaid
graph TD
    User[👤 User] --> App[📱 Women Safety App]
    App --> User
    
    EmergencyServices[🚨 Emergency Services] --> App
    App --> EmergencyServices
    
    EmergencyContacts[👥 Emergency Contacts] --> App
    App --> EmergencyContacts
    
    LocationServices[🌍 Location Services] --> App
    App --> LocationServices
    
    SMSGateway[📱 SMS Gateway] --> App
    App --> SMSGateway
    
    User -.->|Login/Signup<br/>Profile Data<br/>SOS Triggers| App
    App -.->|Notifications<br/>Status Updates| User
    App -.->|Emergency Alerts| EmergencyServices
    EmergencyServices -.->|Response Confirmation| App
    App -.->|SMS Notifications<br/>Location Updates| EmergencyContacts
    EmergencyContacts -.->|Acknowledgments| App
    LocationServices -.->|GPS Data<br/>Address Info| App
    App -.->|Location Requests| LocationServices
    App -.->|SMS Messages| SMSGateway
    SMSGateway -.->|Delivery Status| App
```

## Level 1 DFD (Main Processes)

```mermaid
graph TD
    User[👤 User]
    EmergencyServices[🚨 Emergency Services]
    EmergencyContacts[👥 Emergency Contacts]
    LocationServices[🌍 Location Services]
    SMSGateway[📱 SMS Gateway]
    
    subgraph "Women Safety App"
        Auth[1.0<br/>Authenticate<br/>User]
        ManageContacts[2.0<br/>Manage<br/>Contacts]
        EmergencyResponse[3.0<br/>Emergency<br/>Response]
        SafetyFeatures[4.0<br/>Safety<br/>Features]
        LocationProc[5.0<br/>Location<br/>Services]
        ProfileMgmt[6.0<br/>Profile<br/>Management]
    end
    
    subgraph "Data Stores"
        D1[(D1<br/>User Data)]
        D2[(D2<br/>Contacts)]
        D3[(D3<br/>Location Data)]
        D4[(D4<br/>Session Data)]
        D5[(D5<br/>Emergency Log)]
        D6[(D6<br/>Check-in Data)]
        D7[(D7<br/>Settings)]
    end
    
    %% User Interactions
    User -->|Login Credentials| Auth
    Auth -->|Session Token| User
    User -->|Contact CRUD| ManageContacts
    ManageContacts -->|Contact List| User
    User -->|SOS Trigger| EmergencyResponse
    EmergencyResponse -->|Emergency Status| User
    User -->|Feature Activation| SafetyFeatures
    SafetyFeatures -->|Feature Status| User
    User -->|Profile Updates| ProfileMgmt
    ProfileMgmt -->|Profile Data| User
    
    %% Data Store Interactions
    Auth <--> D1
    Auth <--> D4
    ManageContacts <--> D2
    EmergencyResponse <--> D2
    EmergencyResponse <--> D5
    SafetyFeatures <--> D3
    SafetyFeatures <--> D6
    LocationProc <--> D3
    ProfileMgmt <--> D1
    ProfileMgmt <--> D7
    
    %% External System Interactions
    LocationServices -->|GPS Data| LocationProc
    EmergencyResponse -->|Emergency Alert| EmergencyServices
    EmergencyResponse -->|Notifications| EmergencyContacts
    EmergencyResponse -->|SMS Messages| SMSGateway
    SafetyFeatures -->|Location Sharing| EmergencyContacts
    
    %% Styling
    classDef processStyle fill:#e3f2fd,stroke:#1976d2,stroke-width:2px
    classDef entityStyle fill:#fff3e0,stroke:#f57c00,stroke-width:2px
    classDef datastoreStyle fill:#e8f5e8,stroke:#388e3c,stroke-width:2px
    
    class Auth,ManageContacts,EmergencyResponse,SafetyFeatures,LocationProc,ProfileMgmt processStyle
    class User,EmergencyServices,EmergencyContacts,LocationServices,SMSGateway entityStyle
    class D1,D2,D3,D4,D5,D6,D7 datastoreStyle
```

## Emergency Response Process (Level 2)

```mermaid
graph TD
    User[👤 User]
    EmergencyServices[🚨 Emergency Services]
    EmergencyContacts[👥 Emergency Contacts]
    SMSGateway[📱 SMS Gateway]
    
    subgraph "Emergency Response System"
        Activate[3.1<br/>Activate<br/>Emergency]
        Notify[3.2<br/>Notify<br/>Contacts]
        LogIncident[3.3<br/>Log<br/>Incident]
        TrackResponse[3.4<br/>Track<br/>Response]
    end
    
    subgraph "Data Stores"
        D2[(D2<br/>Contacts Store)]
        D3[(D3<br/>Location Store)]
        D5[(D5<br/>Emergency Log)]
    end
    
    %% Process Flow
    User -->|SOS Trigger<br/>Location Data| Activate
    Activate -->|Get Contacts| D2
    D2 -->|Contact List| Activate
    Activate -->|Emergency Data| Notify
    Notify -->|Log Notification| D5
    Notify -->|Emergency SMS| EmergencyContacts
    Notify -->|Send Messages| SMSGateway
    Notify -->|Alert Services| EmergencyServices
    Notify -->|Incident Data| LogIncident
    LogIncident -->|Store Incident| D5
    Activate -->|Store Location| D3
    TrackResponse -->|Update Status| D5
    SMSGateway -->|Delivery Status| TrackResponse
    EmergencyContacts -->|Acknowledgment| TrackResponse
    EmergencyServices -->|Response Status| TrackResponse
    
    %% Styling
    classDef processStyle fill:#ffe5e5,stroke:#d32f2f,stroke-width:2px
    classDef entityStyle fill:#fff3e0,stroke:#f57c00,stroke-width:2px
    classDef datastoreStyle fill:#e8f5e8,stroke:#388e3c,stroke-width:2px
    
    class Activate,Notify,LogIncident,TrackResponse processStyle
    class User,EmergencyServices,EmergencyContacts,SMSGateway entityStyle
    class D2,D3,D5 datastoreStyle
```

## User Journey Flow

```mermaid
journey
    title Women Safety App User Journey
    section Authentication
      Open App           : 5: User
      Login/Signup       : 4: User
      Verify Identity    : 3: System
      Grant Access       : 5: System
    
    section Setup
      Add Emergency Contacts : 5: User
      Configure Settings     : 4: User
      Set Preferences       : 4: User
      Test Features         : 3: User
    
    section Daily Usage
      Check Safety Status   : 5: User
      Share Location       : 4: User
      Start Check-in       : 4: User
      Complete Check-in    : 5: User
    
    section Emergency
      Trigger SOS          : 1: User
      Send Alerts          : 5: System
      Notify Contacts      : 5: System
      Track Response       : 4: System
      Resolve Incident     : 5: User, System
```

## Database Entity Relationship

```mermaid
erDiagram
    USERS {
        uuid id PK
        varchar email UK
        varchar password_hash
        varchar full_name
        varchar phone_number UK
        date date_of_birth
        text profile_image_url
        boolean is_active
        timestamp created_at
        timestamp updated_at
    }
    
    EMERGENCY_CONTACTS {
        uuid id PK
        uuid user_id FK
        varchar name
        varchar phone_number
        varchar relationship
        boolean is_primary
        integer priority_order
        timestamp created_at
        timestamp updated_at
    }
    
    EMERGENCY_INCIDENTS {
        uuid id PK
        uuid user_id FK
        varchar incident_type
        varchar status
        decimal latitude
        decimal longitude
        text address
        text message
        timestamp created_at
        timestamp resolved_at
    }
    
    USER_LOCATIONS {
        uuid id PK
        uuid user_id FK
        decimal latitude
        decimal longitude
        text address
        varchar location_type
        decimal accuracy_meters
        timestamp created_at
    }
    
    SAFETY_CHECKINS {
        uuid id PK
        uuid user_id FK
        timestamp expected_checkin_time
        timestamp actual_checkin_time
        varchar status
        integer duration_minutes
        text message
        timestamp created_at
    }
    
    USER_SETTINGS {
        uuid id PK
        uuid user_id FK
        varchar setting_key
        text setting_value
        varchar data_type
        timestamp created_at
        timestamp updated_at
    }
    
    EMERGENCY_NOTIFICATIONS {
        uuid id PK
        uuid incident_id FK
        uuid contact_id FK
        varchar notification_type
        varchar status
        text message
        timestamp sent_at
        timestamp delivered_at
    }
    
    %% Relationships
    USERS ||--o{ EMERGENCY_CONTACTS : "has many"
    USERS ||--o{ EMERGENCY_INCIDENTS : "creates"
    USERS ||--o{ USER_LOCATIONS : "tracked at"
    USERS ||--o{ SAFETY_CHECKINS : "schedules"
    USERS ||--o{ USER_SETTINGS : "configures"
    EMERGENCY_INCIDENTS ||--o{ EMERGENCY_NOTIFICATIONS : "generates"
    EMERGENCY_CONTACTS ||--o{ EMERGENCY_NOTIFICATIONS : "receives"
```

## System Architecture

```mermaid
graph TB
    subgraph "Mobile App Layer"
        Android[📱 Android App<br/>Jetpack Compose]
        AuthUI[🔐 Authentication UI]
        ContactsUI[👥 Contacts UI]
        EmergencyUI[🚨 Emergency UI]
        FeaturesUI[🛡️ Safety Features UI]
        ProfileUI[⚙️ Profile UI]
    end
    
    subgraph "API Gateway"
        Gateway[🌐 API Gateway<br/>Authentication & Routing]
    end
    
    subgraph "Backend Services"
        AuthService[🔐 Auth Service]
        ContactService[👥 Contact Service]
        EmergencyService[🚨 Emergency Service]
        LocationService[📍 Location Service]
        NotificationService[📢 Notification Service]
    end
    
    subgraph "Data Layer"
        PostgreSQL[(🗄️ PostgreSQL<br/>Main Database)]
        Redis[(⚡ Redis<br/>Session & Cache)]
        FileStorage[📁 File Storage<br/>Images & Docs]
    end
    
    subgraph "External Services"
        GPS[🌍 GPS Services]
        SMS[📱 SMS Gateway]
        Emergency911[🚨 Emergency Services]
        Maps[🗺️ Maps API]
    end
    
    %% Mobile to API
    Android --> Gateway
    AuthUI --> Gateway
    ContactsUI --> Gateway
    EmergencyUI --> Gateway
    FeaturesUI --> Gateway
    ProfileUI --> Gateway
    
    %% Gateway to Services
    Gateway --> AuthService
    Gateway --> ContactService
    Gateway --> EmergencyService
    Gateway --> LocationService
    Gateway --> NotificationService
    
    %% Services to Data
    AuthService --> PostgreSQL
    AuthService --> Redis
    ContactService --> PostgreSQL
    EmergencyService --> PostgreSQL
    LocationService --> PostgreSQL
    NotificationService --> PostgreSQL
    
    %% External Integrations
    LocationService --> GPS
    LocationService --> Maps
    NotificationService --> SMS
    EmergencyService --> Emergency911
    ProfileUI --> FileStorage
    
    %% Styling
    classDef mobileStyle fill:#e8f5e8,stroke:#4caf50,stroke-width:2px
    classDef apiStyle fill:#fff3e0,stroke:#ff9800,stroke-width:2px
    classDef serviceStyle fill:#e3f2fd,stroke:#2196f3,stroke-width:2px
    classDef dataStyle fill:#fce4ec,stroke:#e91e63,stroke-width:2px
    classDef externalStyle fill:#f3e5f5,stroke:#9c27b0,stroke-width:2px
    
    class Android,AuthUI,ContactsUI,EmergencyUI,FeaturesUI,ProfileUI mobileStyle
    class Gateway apiStyle
    class AuthService,ContactService,EmergencyService,LocationService,NotificationService serviceStyle
    class PostgreSQL,Redis,FileStorage dataStyle
    class GPS,SMS,Emergency911,Maps externalStyle
```

## State Diagram - Emergency Process

```mermaid
stateDiagram-v2
    [*] --> Idle
    
    Idle --> EmergencyTriggered : SOS Button Pressed
    EmergencyTriggered --> ValidatingLocation : Get GPS Location
    ValidatingLocation --> NotifyingContacts : Location Found
    ValidatingLocation --> NotifyingContacts : Location Timeout (5s)
    
    NotifyingContacts --> SendingSMS : Get Emergency Contacts
    NotifyingContacts --> AlertingServices : No Contacts Found
    
    SendingSMS --> AlertingServices : SMS Sent
    SendingSMS --> AlertingServices : SMS Failed
    
    AlertingServices --> WaitingResponse : Alert Sent
    WaitingResponse --> IncidentLogged : Response Received
    WaitingResponse --> IncidentLogged : Timeout (30s)
    
    IncidentLogged --> Resolved : User Cancels
    IncidentLogged --> Resolved : Emergency Resolved
    
    Resolved --> Idle : Reset System
    
    %% Error States
    EmergencyTriggered --> ErrorState : System Error
    ValidatingLocation --> ErrorState : GPS Error
    SendingSMS --> ErrorState : Network Error
    AlertingServices --> ErrorState : Service Error
    
    ErrorState --> Idle : Retry/Reset
```

## Sequence Diagram - SOS Flow

```mermaid
sequenceDiagram
    participant U as User
    participant A as Android App
    participant API as API Gateway
    participant ES as Emergency Service
    participant NS as Notification Service
    participant DB as Database
    participant SMS as SMS Gateway
    participant EC as Emergency Contacts
    
    U->>A: Press SOS Button
    A->>A: Get Current Location
    A->>API: POST /emergency/sos
    API->>ES: Create Emergency Incident
    ES->>DB: Store Incident
    ES->>NS: Trigger Notifications
    
    par Notify Contacts
        NS->>DB: Get Emergency Contacts
        DB-->>NS: Return Contacts List
        NS->>SMS: Send Emergency SMS
        SMS-->>EC: Deliver SMS
        EC-->>SMS: Delivery Confirmation
        SMS-->>NS: Confirm Delivery
    and Alert Services
        NS->>Emergency911: Send Alert
        Emergency911-->>NS: Acknowledge
    end
    
    NS->>ES: Update Notification Status
    ES->>DB: Log Notifications
    ES-->>API: Return Incident ID
    API-->>A: Emergency Created
    A-->>U: Show Emergency Status
    
    Note over U,EC: Emergency contacts receive<br/>SMS with location and<br/>emergency message
```


