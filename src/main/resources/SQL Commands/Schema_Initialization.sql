-- Create Event Table
CREATE TABLE event (
    event_id INT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    start_datetime DATETIME,
    end_datetime DATETIME,
    venue_id INT,
    organizer_id INT,
    FOREIGN KEY (venue_id) REFERENCES venue(venue_id),
    FOREIGN KEY (organizer_id) REFERENCES organizer(organizer_id)
);

CREATE TABLE event_coordinator (
    coordinator_id INT PRIMARY KEY,
    coordinator_name VARCHAR(255),
    coordinator_email VARCHAR(255),
    coordinator_phoneno VARCHAR(255),
    coordinator_area VARCHAR(255)
)

-- Create Venue Table
CREATE TABLE venue (
    venue_id INT PRIMARY KEY,
    name VARCHAR(255),
    address TEXT,
    capacity INT,
    contact_person VARCHAR(255),
    contact_email VARCHAR(255),
    contact_phone VARCHAR(20)
);

-- Create Organizer Table
CREATE TABLE organizer (
    organizer_id INT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20)
);

-- Create Attendee Table
CREATE TABLE attendee (
    attendee_id INT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    relationship VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20)
);

-- Create Event_Attendee Table (Many-to-Many Relationship)
CREATE TABLE event_attendee (
    event_id INT,
    attendee_id INT,
    registration_date DATETIME,
    PRIMARY KEY (event_id, attendee_id),
    FOREIGN KEY (event_id) REFERENCES event(event_id),
    FOREIGN KEY (attendee_id) REFERENCES attendee(attendee_id)
);

-- Create Event_Category Table
CREATE TABLE event_category (
    category_id INT PRIMARY KEY,
    category_name VARCHAR(255)
);

-- Create Event_EventCategory Table (Many-to-Many Relationship)
CREATE TABLE event_eventcategory (
    event_id INT,
    category_id INT,
    PRIMARY KEY (event_id, category_id),
    FOREIGN KEY (event_id) REFERENCES event(event_id),
    FOREIGN KEY (category_id) REFERENCES event_category(category_id)
);

-- Create Registration Table
CREATE TABLE registration (
    registration_id INT PRIMARY KEY,
    event_id INT,
    attendee_id INT,
    registration_date DATETIME,
    FOREIGN KEY (event_id) REFERENCES event(event_id),
    FOREIGN KEY (attendee_id) REFERENCES attendee(attendee_id)
);
-- Create Rooms Table
CREATE TABLE hotel_rooms (
    room_number INT PRIMARY KEY,
    attendee_id INT,
    staying_on_date DATETIME,
    FOREIGN KEY (event_id) REFERENCES event(event_id),
    FOREIGN KEY (attendee_id) REFERENCES attendee(attendee_id)
);

