package com.palash.mtbmle.data.mock

import com.palash.mtbmle.data.model.TranslationExample

/**
 * DEMO classroom sentence pairs for the prototype only.
 *
 * These are illustrative placeholders written for UI development — they are NOT
 * linguistically validated Santhali translations. Per the roadmap, do not present
 * these as accurate or model-generated. Replace this entire list with a real,
 * native-speaker-validated hindi,santhali,source dataset from the Data/Backend team
 * (see /data/cleaned/ in the repo root) once available.
 */
object MockTranslationData {
    val examples = listOf(
        TranslationExample(
            hindi = "बच्चों, अपनी किताब खोलो।",
            santhaliOlChiki = "ᱜᱤᱫᱽᱨᱟᱹ ᱠᱚ, ᱟᱯᱱᱟᱨᱟᱜ ᱯᱩᱛᱷᱤ ᱡᱷᱤᱡᱽ ᱯᱮ।",
            santhaliDevanagari = "गिद्रा को, अपनाराग् पुथि झिज् पे।"
        ),
        TranslationExample(
            hindi = "आज हम गिनती सीखेंगे।",
            santhaliOlChiki = "ᱛᱮᱦᱮᱧ ᱵᱚᱱ ᱞᱮᱠᱷᱟ ᱵᱚᱱ ᱪᱮᱫᱟ।",
            santhaliDevanagari = "तेहेञ् बोन् लेखा बोन् चेदा।"
        ),
        TranslationExample(
            hindi = "यह कौन सा रंग है?",
            santhaliOlChiki = "ᱱᱚᱣᱟ ᱫᱚ ᱚᱠᱟ ᱨᱚᱝ ᱠᱟᱱᱟ?",
            santhaliDevanagari = "नोवा दो ओका रंग काना?"
        ),
        TranslationExample(
            hindi = "बहुत बढ़िया, तुमने सही जवाब दिया।",
            santhaliOlChiki = "ᱟᱹᱰᱤ ᱵᱮᱥ, ᱟᱢ ᱥᱟᱹᱦᱤ ᱨᱚᱲ ᱠᱮᱫᱟ।",
            santhaliDevanagari = "अड़ि बेस, आम् सही रोड़ केदा।"
        ),
        // keep your existing entries below this, updated with the new field names
            hindi = "बच्चों, अपनी किताब खोलो।",
            santhali = "[Demo translation — Open your book]"
        ),
        TranslationExample(
            hindi = "नमस्ते बच्चों।",
            santhali = "[Demo translation — Greeting students]"
        ),
        TranslationExample(
            hindi = "अपनी जगह पर बैठो।",
            santhali = "[Demo translation — Sit down]"
        ),
        TranslationExample(
            hindi = "खड़े हो जाओ।",
            santhali = "[Demo translation — Stand up]"
        ),
        TranslationExample(
            hindi = "ध्यान से सुनो।",
            santhali = "[Demo translation — Listen carefully]"
        ),
        TranslationExample(
            hindi = "यह शब्द पढ़ो।",
            santhali = "[Demo translation — Read this word]"
        ),
        TranslationExample(
            hindi = "उत्तर लिखो।",
            santhali = "[Demo translation — Write the answer]"
        ),
        TranslationExample(
            hindi = "संख्याएँ गिनो।",
            santhali = "[Demo translation — Count the numbers]"
        ),
        TranslationExample(
            hindi = "चित्र को देखो।",
            santhali = "[Demo translation — Look at the picture]"
        ),
        TranslationExample(
            hindi = "यह क्या है?",
            santhali = "[Demo translation — What is this?]"
        ),
        TranslationExample(
            hindi = "मेरे पीछे दोहराओ।",
            santhali = "[Demo translation — Repeat after me]"
        ),
        TranslationExample(
            hindi = "बहुत अच्छा!",
            santhali = "[Demo translation — Very good]"
        ),
        TranslationExample(
            hindi = "फिर से कोशिश करो।",
            santhali = "[Demo translation — Try again]"
        )
    )
}
