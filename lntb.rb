dir = File.expand_path(File.dirname(__FILE__))
if ARGV[0] == "destroy"
	`rm -rf #{dir}/../control`
end
