require: functions.js
require: slotfilling/slotFilling.sc
  module = sys.zb-common

theme: /

    state: Start
        q!: $regex</start>
        a: Молви, друг, и войди!
        
        state: Melon
            q: * (melon|мелон) *
            a: Перед тобой три коридора, в какой пойдешь?
        
            state: GoLeft:
                q: * *лев* *
                a: Тебя съел дракон! Не хотите сыграть снова?
                
                state: PlayAgain:
                    q: да
                    go!: /Start
            
            state: GoRight:
                q: * *прав* *
                a: Разрабы - дауны и не придумали эту сюжетную ветку(
            
            state: GoStraight:
                q: * *прям* *
                a: Перед вами сундук. Что будете делать?
                
                state: OpenChest:
                    q: * (~открывать|~вскрывать) *
                    script:
                        $session.money = getRandomInt(10);
                        $reactions.answer("Вы получили " + $session.money + " монет!");
                        $reactions.answer("Хотите купить меч?");
                        $reactions.transition("./BuySword");
                    
                    state: BuySword:
                        state: Yes
                            q: да
                            script:
                                if($session.money - 10 < 0)
                                {
                                    $reactions.answer("вы не можете купить этот меч, но хотите испытать удачу? я приумножу ваши деньги на рандомное число, что скажите?")
                                    $reactions.transition("./AgreeLotery");
                                }
                                else 
                                {
                                    $session.money -= 10;
                                    $reactions.answer("Поздравляю с покупкой! теперь у вас осталось " + $session.money + " монет!")
                                }
                 
                    state: AgreeLotery
                        state: Yes
                            q: * *(да|~дать)* *
                            script:
                                $session.money *= getRandomInt(10)
                                $reactions.answer("У вас теперь " + $session.money + " монет!")
                                
                                if($session.money - 10 >= 0)
                                {
                                    $reactions.transition("/BuySword");
                                }
                                else 
                                {
                                    $reactions.answer("Вам все равно не хватает!")
                                }
                    
        state: NoMelon
            event: noMatch
            a: На эльфийском...
    
    state: NoMatch
        event!: noMatch
        a: Попробуйте сказать это на эльфийском
    
   