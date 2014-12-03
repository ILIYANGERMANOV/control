def check_message_format
	missed_revs = `git rev-list #{$oldrev}..#{$newrev}`.split("\n")
	missed_revs.each do |rev|
		message = `git cat-file commit #{rev}`
		sentence = message.split(".").first
    	exit 1 if sentence == nil or sentence.capitalize != sentence or sentence.length > 50
	end
end
check_message_format
