import pandas as pd
from faker import Faker
import random
import numpy as np

# Ініціалізація Faker для англійської та української мов
fake_en = Faker('en_US')
fake_ua = Faker('uk_UA')

# --- Списки можливих значень (Ваші розширені дані) ---

# --- АНГЛІЙСЬКА МОВА ---
JOB_TITLES_EN = [
    'Python Developer', 'Java Developer', 'Frontend Developer', 'Data Scientist', 'QA Engineer', 'DevOps Engineer',
    'Project Manager', 'Backend Developer', 'Fullstack Developer', 'Mobile Developer', 'Business Analyst',
    'UX/UI Designer',
    'Machine Learning Engineer', 'Data Engineer', 'Software Architect', 'Security Engineer', 'Product Manager',
    'Scrum Master',
    'Database Administrator', 'Network Engineer', 'Cloud Engineer', 'AI Researcher', 'Blockchain Developer',
    'Game Developer',
    'Embedded Systems Engineer', 'Site Reliability Engineer', 'Data Analyst', 'Technical Writer', 'Support Engineer',
    'Sales Engineer', 'Systems Analyst', 'IT Consultant', 'Automation Engineer', 'Performance Engineer', 'BI Developer'
]
SKILLS_EN = {
    'Python Developer': ['Python', 'Django', 'Flask', 'SQL', 'Docker', 'Git', 'REST API', 'FastAPI', 'Celery', 'Pytest',
                         'asyncio', 'SQLAlchemy'],
    'Java Developer': ['Java', 'Spring', 'Hibernate', 'SQL', 'Maven', 'Git', 'Microservices', 'JUnit', 'Kotlin',
                       'Gradle', 'Spring Boot', 'JPA'],
    'Frontend Developer': ['JavaScript', 'React', 'Vue', 'HTML', 'CSS', 'Webpack', 'TypeScript', 'SASS', 'Angular',
                           'Bootstrap', 'Redux', 'Next.js'],
    'Data Scientist': ['Python', 'Pandas', 'NumPy', 'Scikit-learn', 'TensorFlow', 'SQL', 'Machine Learning',
                       'Data Visualization', 'PyTorch', 'Keras', 'Jupyter', 'Big Data'],
    'QA Engineer': ['Testing', 'Selenium', 'Jira', 'SQL', 'API Testing', 'CI/CD', 'Load Testing', 'Postman', 'Appium',
                    'Cypress', 'Jenkins', 'TestRail'],
    'DevOps Engineer': ['Docker', 'Kubernetes', 'AWS', 'CI/CD', 'Terraform', 'Ansible', 'Monitoring', 'Linux',
                        'Jenkins', 'GitLab', 'Prometheus', 'ELK Stack'],
    'Project Manager': ['Agile', 'Scrum', 'Jira', 'Project Management', 'Communication', 'Risk Management', 'Kanban',
                        'Stakeholder Management', 'Budgeting', 'MS Project'],
    'Backend Developer': ['Node.js', 'Express', 'MongoDB', 'SQL', 'Docker', 'Git', 'REST API', 'GraphQL',
                          'Ruby on Rails', 'PHP', 'Laravel', 'PostgreSQL'],
    'Fullstack Developer': ['JavaScript', 'React', 'Node.js', 'Python', 'SQL', 'Docker', 'REST API', 'Vue', 'Angular',
                            'MongoDB', 'AWS', 'Git'],
    'Mobile Developer': ['Swift', 'Kotlin', 'React Native', 'Flutter', 'Git', 'API Integration', 'Android Studio',
                         'Xcode', 'Firebase', 'Ionic'],
    'Business Analyst': ['Requirements Gathering', 'Data Analysis', 'SQL', 'Communication', 'Documentation', 'UML',
                         'BPMN', 'Agile', 'Stakeholder Interviews'],
    'UX/UI Designer': ['Figma', 'Sketch', 'Adobe XD', 'Prototyping', 'User Research', 'Wireframing', 'Photoshop',
                       'Illustrator', 'InVision', 'Usability Testing'],
    'Machine Learning Engineer': ['Python', 'TensorFlow', 'PyTorch', 'Scikit-learn', 'Deep Learning', 'NLP',
                                  'Computer Vision', 'Kubernetes', 'MLOps', 'Spark'],
    'Data Engineer': ['Python', 'SQL', 'ETL', 'Spark', 'Hadoop', 'AWS', 'Kafka', 'Airflow', 'BigQuery',
                      'Data Pipelines', 'NoSQL'],
    'Software Architect': ['Design Patterns', 'Microservices', 'UML', 'Cloud Architecture', 'API Design', 'Security',
                           'Scalability', 'Java', 'Python', 'DevOps'],
    'Security Engineer': ['Cybersecurity', 'Penetration Testing', 'Firewall', 'Encryption', 'SIEM',
                          'Vulnerability Assessment', 'OWASP', 'CISSP', 'Network Security'],
    'Product Manager': ['Product Roadmap', 'User Stories', 'Market Research', 'Agile', 'Prioritization', 'A/B Testing',
                        'Analytics', 'Stakeholder Management'],
    'Scrum Master': ['Scrum', 'Agile', 'Jira', 'Facilitation', 'Coaching', 'Sprint Planning', 'Retrospectives',
                     'Kanban', 'Team Building'],
    'Database Administrator': ['SQL', 'Oracle', 'MySQL', 'PostgreSQL', 'Backup', 'Performance Tuning', 'Data Modeling',
                               'MongoDB', 'Redis'],
    'Network Engineer': ['Cisco', 'Routing', 'Switching', 'Firewall', 'VPN', 'TCP/IP', 'BGP', 'OSPF',
                         'Network Security', 'Wireless'],
    'Cloud Engineer': ['AWS', 'Azure', 'GCP', 'Terraform', 'Kubernetes', 'CloudFormation', 'Serverless', 'IAM',
                       'Networking', 'Migration'],
    'AI Researcher': ['Python', 'Deep Learning', 'NLP', 'Computer Vision', 'Reinforcement Learning', 'Research Papers',
                      'PyTorch', 'TensorFlow', 'Ethics in AI'],
    'Blockchain Developer': ['Solidity', 'Ethereum', 'Smart Contracts', 'Web3', 'Bitcoin', 'Hyperledger',
                             'Cryptography', 'Node.js', 'Truffle'],
    'Game Developer': ['Unity', 'Unreal Engine', 'C#', 'C++', '3D Modeling', 'Game Design', 'Physics', 'Multiplayer',
                       'VR/AR'],
    'Embedded Systems Engineer': ['C', 'C++', 'Microcontrollers', 'RTOS', 'Firmware', 'Hardware Design', 'IoT',
                                  'Assembly', 'Debugging'],
    'Site Reliability Engineer': ['Monitoring', 'Incident Response', 'Automation', 'Python', 'Go', 'Kubernetes', 'SLA',
                                  'On-Call', 'Infrastructure as Code'],
    'Data Analyst': ['SQL', 'Excel', 'Tableau', 'Power BI', 'Python', 'Data Visualization', 'Statistics', 'R',
                     'Google Analytics'],
    'Technical Writer': ['Documentation', 'Markdown', 'API Docs', 'User Guides', 'Confluence',
                         'Technical Communication', 'Editing', 'Research'],
    'Support Engineer': ['Troubleshooting', 'Customer Support', 'Ticketing Systems', 'SQL', 'Networking', 'Linux',
                         'Windows', 'Remote Assistance'],
    'Sales Engineer': ['Technical Sales', 'Product Demos', 'Customer Needs Analysis', 'CRM', 'Presentation Skills',
                       'Negotiation', 'Industry Knowledge'],
    'Systems Analyst': ['System Design', 'Requirements Analysis', 'SQL', 'UML', 'Integration', 'Testing',
                        'Business Processes'],
    'IT Consultant': ['Consulting', 'Project Delivery', 'Client Management', 'Technology Strategy', 'Risk Assessment',
                      'Cloud Solutions'],
    'Automation Engineer': ['Selenium', 'Python', 'Jenkins', 'CI/CD', 'Scripting', 'Robotics', 'Process Automation',
                            'BPM'],
    'Performance Engineer': ['Load Testing', 'JMeter', 'Performance Tuning', 'Monitoring', 'Profiling', 'Scalability',
                             'Database Optimization'],
    'BI Developer': ['SQL', 'ETL', 'Tableau', 'Power BI', 'Data Warehousing', 'SSIS', 'SSRS', 'OLAP', 'Big Data']
}
SOFT_SKILLS_EN = [
    'Communication', 'Teamwork', 'Problem Solving', 'Responsibility', 'Leadership', 'Creativity', 'Adaptability',
    'Time Management',
    'Critical Thinking', 'Conflict Resolution', 'Emotional Intelligence', 'Initiative', 'Resilience', 'Collaboration',
    'Decision Making',
    'Negotiation', 'Attention to Detail', 'Learning Agility', 'Empathy', 'Stress Management', 'Self-Motivation',
    'Flexibility',
    'Analytical Thinking', 'Innovation', 'Interpersonal Skills'
]
LOCATIONS_EN = [
    'New York', 'San Francisco', 'Los Angeles', 'Chicago', 'Austin', 'Remote', 'Seattle', 'Boston', 'Miami', 'Denver',
    'Washington D.C.',
    'Atlanta', 'Dallas', 'Houston', 'Philadelphia', 'Portland', 'San Diego', 'Phoenix', 'Orlando', 'Las Vegas',
    'London', 'Berlin',
    'Tokyo', 'Sydney', 'Toronto', 'Singapore', 'Dubai', 'Paris'
]

# --- УКРАЇНСЬКА МОВА ---
JOB_TITLES_UA = [
    'Розробник Python', 'Розробник Java', 'Фронтенд-розробник', 'Науковець з даних', 'Інженер QA', 'Інженер DevOps',
    'Менеджер проекту', 'Бекенд-розробник', 'Фулстек-розробник', 'Розробник мобільних додатків', 'Бізнес-аналітик',
    'Дизайнер UX/UI',
    'Інженер машинного навчання', 'Інженер даних', 'Архітектор програмного забезпечення', 'Інженер безпеки',
    'Менеджер продукту', 'Scrum Master',
    'Адміністратор баз даних', 'Мережевий інженер', 'Хмарний інженер', 'Дослідник ШІ', 'Розробник блокчейну',
    'Розробник ігор',
    'Інженер вбудованих систем', 'Інженер надійності сайту', 'Аналітик даних', 'Технічний письменник',
    'Інженер підтримки',
    'Інженер з продажів', 'Системний аналітик', 'IT-консультант', 'Інженер автоматизації', 'Інженер продуктивності',
    'Розробник BI'
]
# Важливо: Ключі в SKILLS_UA мають відповідати значенням у JOB_TITLES_UA
SKILLS_UA = {
    'Розробник Python': SKILLS_EN['Python Developer'], 'Розробник Java': SKILLS_EN['Java Developer'],
    'Фронтенд-розробник': SKILLS_EN['Frontend Developer'],
    'Науковець з даних': SKILLS_EN['Data Scientist'], 'Інженер QA': SKILLS_EN['QA Engineer'],
    'Інженер DevOps': SKILLS_EN['DevOps Engineer'],
    'Менеджер проекту': SKILLS_EN['Project Manager'], 'Бекенд-розробник': SKILLS_EN['Backend Developer'],
    'Фулстек-розробник': SKILLS_EN['Fullstack Developer'],
    'Розробник мобільних додатків': SKILLS_EN['Mobile Developer'], 'Бізнес-аналітик': SKILLS_EN['Business Analyst'],
    'Дизайнер UX/UI': SKILLS_EN['UX/UI Designer'],
    'Інженер машинного навчання': SKILLS_EN['Machine Learning Engineer'], 'Інженер даних': SKILLS_EN['Data Engineer'],
    'Архітектор програмного забезпечення': SKILLS_EN['Software Architect'],
    'Інженер безпеки': SKILLS_EN['Security Engineer'], 'Менеджер продукту': SKILLS_EN['Product Manager'],
    'Scrum Master': SKILLS_EN['Scrum Master'],
    'Адміністратор баз даних': SKILLS_EN['Database Administrator'], 'Мережевий інженер': SKILLS_EN['Network Engineer'],
    'Хмарний інженер': SKILLS_EN['Cloud Engineer'],
    'Дослідник ШІ': SKILLS_EN['AI Researcher'], 'Розробник блокчейну': SKILLS_EN['Blockchain Developer'],
    'Розробник ігор': SKILLS_EN['Game Developer'],
    'Інженер вбудованих систем': SKILLS_EN['Embedded Systems Engineer'],
    'Інженер надійності сайту': SKILLS_EN['Site Reliability Engineer'],
    'Аналітик даних': SKILLS_EN['Data Analyst'], 'Технічний письменник': SKILLS_EN['Technical Writer'],
    'Інженер підтримки': SKILLS_EN['Support Engineer'],
    'Інженер з продажів': SKILLS_EN['Sales Engineer'], 'Системний аналітик': SKILLS_EN['Systems Analyst'],
    'IT-консультант': SKILLS_EN['IT Consultant'],
    'Інженер автоматизації': SKILLS_EN['Automation Engineer'],
    'Інженер продуктивності': SKILLS_EN['Performance Engineer'], 'Розробник BI': SKILLS_EN['BI Developer']
}
SOFT_SKILLS_UA = [
    'Комунікація', 'Командна робота', 'Вирішення проблем', 'Відповідальність', 'Лідерство', 'Креативність',
    'Адаптивність', 'Управління часом',
    'Критичне мислення', 'Вирішення конфліктів', 'Емоційний інтелект', 'Ініціатива', 'Стійкість', 'Співпраця',
    'Прийняття рішень',
    'Переговори', 'Увага до деталей', 'Швидке навчання', 'Емпатія', 'Управління стресом', 'Самомотивація', 'Гнучкість',
    'Аналітичне мислення', 'Інноваційність', 'Міжособистісні навички'
]
LOCATIONS_UA = [
    'Київ', 'Львів', 'Харків', 'Одеса', 'Дніпро', 'Віддалено', 'Запоріжжя', 'Вінниця', 'Івано-Франківськ', 'Тернопіль',
    'Чернівці', 'Ужгород', 'Рівне', 'Луцьк', 'Хмельницький', 'Житомир', 'Черкаси', 'Кропивницький', 'Миколаїв',
    'Херсон', 'New York', 'San Francisco',
    'London', 'Berlin', 'Warsaw', 'Prague', 'Amsterdam', 'Stockholm'
]

# Перемішуємо списки для випадковості
for lst in [JOB_TITLES_EN, SOFT_SKILLS_EN, LOCATIONS_EN, JOB_TITLES_UA, SOFT_SKILLS_UA, LOCATIONS_UA]:
    random.shuffle(lst)
for skill_list in SKILLS_EN.values():
    random.shuffle(skill_list)
for skill_list in SKILLS_UA.values():
    random.shuffle(skill_list)


def add_noise(value, probability=0.05):
    """Допоміжна функція для додавання 'шуму' (пропущених значень) до даних."""
    if random.random() < probability:
        return np.nan
    return value


def generate_vacancies(lang, num_records):
    """Генерує датасет вакансій з реалістичними даними та шумом."""
    if lang == 'en':
        fake, job_titles, skills_dict, locations, desc_template = (
            fake_en, JOB_TITLES_EN, SKILLS_EN, LOCATIONS_EN,
            "We are looking for a talented {title} with {experience}+ years of experience. "
            "You will work on exciting projects in the {bs} field. "
            "Key requirements: {skills}. We offer a competitive salary and growth opportunities."
        )
    elif lang == 'ua':
        fake, job_titles, skills_dict, locations, desc_template = (
            fake_ua, JOB_TITLES_UA, SKILLS_UA, LOCATIONS_UA,
            "Ми шукаємо талановитого {title} з {experience}+ роками досвіду. "
            "Ви працюватимете над захоплюючими проектами в сфері {bs}. "
            "Ключові вимоги: {skills}. Ми пропонуємо конкурентну зарплату та можливості для зростання."
        )
    else:
        raise ValueError("Unsupported language")

    data = []
    for i in range(num_records):
        title = random.choice(job_titles)
        required_skills = random.sample(skills_dict.get(title, []), k=random.randint(3, 6))

        # Застосовуємо шум до даних
        experience = add_noise(random.randint(1, 8))
        salary_from = add_noise(random.randint(5, 15) * 1000)
        salary_to = salary_from + random.randint(1, 5) * 1000 if pd.notna(salary_from) else np.nan
        location = add_noise(random.choice(locations))

        description = desc_template.format(
            title=title,
            experience=int(experience) if pd.notna(experience) else "N/A",
            bs=fake.bs(),
            skills=', '.join(required_skills) if required_skills else "Not specified"
        )

        data.append({
            'vacancy_id': f'vac_{lang}_{i}', 'title': title, 'description': description,
            'required_skills': ','.join(required_skills) if required_skills else np.nan,
            'experience_years': experience, 'salary_from': salary_from,
            'salary_to': salary_to, 'location': location
        })
    return pd.DataFrame(data)


def generate_candidates(lang, num_records):
    """Генерує датасет кандидатів з реалістичними даними та шумом."""
    if lang == 'en':
        fake, job_titles, skills_dict, soft_skills, locations, summary_template = (
            fake_en, JOB_TITLES_EN, SKILLS_EN, SOFT_SKILLS_EN, LOCATIONS_EN,
            "Experienced {title} with {experience} years of experience. Strong skills in {skills}. "
            "Looking for new challenges and professional growth opportunities."
        )
    elif lang == 'ua':
        fake, job_titles, skills_dict, soft_skills, locations, summary_template = (
            fake_ua, JOB_TITLES_UA, SKILLS_UA, SOFT_SKILLS_UA, LOCATIONS_UA,
            "Досвідчений {title} з {experience} роками досвіду. Сильні навички в {skills}. "
            "Шукаю нові виклики та можливості для професійного зростання."
        )
    else:
        raise ValueError("Unsupported language")

    data = []
    for i in range(num_records):
        title = random.choice(job_titles)
        candidate_skills = random.sample(skills_dict.get(title, []), k=random.randint(2, 7))
        candidate_skills.extend(random.sample(soft_skills, k=random.randint(1, 3)))

        # Застосовуємо шум до даних
        experience = add_noise(random.randint(0, 10))
        salary_expectation = add_noise(random.randint(4, 20) * 1000)
        preferred_location = add_noise(random.choice(locations))

        summary = summary_template.format(
            title=title,
            experience=int(experience) if pd.notna(experience) else "N/A",
            skills=', '.join(candidate_skills) if candidate_skills else "diverse skills"
        )

        data.append({
            'candidate_id': f'cand_{lang}_{i}', 'title': title, 'summary': summary,
            'skills': ','.join(candidate_skills) if candidate_skills else np.nan,
            'experience_years': experience, 'salary_expectation': salary_expectation,
            'preferred_location': preferred_location
        })
    return pd.DataFrame(data)


if __name__ == "__main__":
    print("Generating multilingual dataset...")
    num_vacancies_per_lang = 1000
    num_candidates_per_lang = 5000

    # Генерація та збереження даних
    for lang in ['en', 'ua']:
        vacancies_df = generate_vacancies(lang, num_vacancies_per_lang)
        candidates_df = generate_candidates(lang, num_candidates_per_lang)

        vacancies_df.to_csv(f'vacancies_{lang}.csv', index=False)
        candidates_df.to_csv(f'candidates_{lang}.csv', index=False)
        print(f"Created {num_vacancies_per_lang} vacancies for '{lang}' in 'vacancies_{lang}.csv'")
        print(f"Created {num_candidates_per_lang} candidates for '{lang}' in 'candidates_{lang}.csv'")

    print("Done!")